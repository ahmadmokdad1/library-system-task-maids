package com.maids.library_management_system.services;


import com.maids.library_management_system.dtos.BorrowingRecordDTO;
import com.maids.library_management_system.exceptions.ResourceAlreadyInUseException;
import com.maids.library_management_system.exceptions.ResourceNotFoundException;
import com.maids.library_management_system.models.Book;
import com.maids.library_management_system.models.BorrowingRecord;
import com.maids.library_management_system.models.Patron;
import com.maids.library_management_system.repositories.BookRepository;
import com.maids.library_management_system.repositories.BorrowingRecordRepository;
import com.maids.library_management_system.repositories.PatronRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;


@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    private final BookRepository bookRepository;

    private  final PatronRepository patronRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BorrowingRecordServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository, ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BorrowingRecordDTO borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("book with id " + bookId + " not found"));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("patron with id " + patronId + " not found"));

        boolean isBookBorrowed = borrowingRecordRepository.findAll()
                .stream()
                .anyMatch(record -> record.getBook().getId().equals(bookId) && record.getReturnDate() == null);

        if (isBookBorrowed) {
            throw new ResourceAlreadyInUseException("book with id " + bookId + " is already borrowed and not returned.");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDateTime.now());

        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return modelMapper.map(savedRecord, BorrowingRecordDTO.class);
    }

    @Override
    @Transactional
    public BorrowingRecordDTO returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findAll()
                .stream()
                .filter(record -> record.getBook().getId().equals(bookId) &&
                        record.getPatron().getId().equals(patronId) &&
                        record.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("borrowing record for book id " + bookId + " and patron id " + patronId + " not found or already returned"));

        borrowingRecord.setReturnDate(LocalDateTime.now());
        BorrowingRecord updatedRecord = borrowingRecordRepository.save(borrowingRecord);
        return modelMapper.map(updatedRecord, BorrowingRecordDTO.class);
    }

    @Override
    public List<BorrowingRecordDTO> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll()
                .stream()
                .map(record -> modelMapper.map(record, BorrowingRecordDTO.class))
                .toList();
    }
}