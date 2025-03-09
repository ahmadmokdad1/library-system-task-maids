package com.maids.library_management_system.services;

import com.maids.library_management_system.dtos.BorrowingRecordDTO;

import java.util.List;

public interface BorrowingRecordService {
    BorrowingRecordDTO borrowBook(Long bookId, Long patronId);
    BorrowingRecordDTO returnBook(Long bookId, Long patronId);
    List<BorrowingRecordDTO> getAllBorrowingRecords();
}
