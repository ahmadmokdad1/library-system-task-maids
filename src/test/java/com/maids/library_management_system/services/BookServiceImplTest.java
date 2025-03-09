package com.maids.library_management_system.services;


import com.maids.library_management_system.dtos.BookDTO;
import com.maids.library_management_system.exceptions.ResourceNotFoundException;
import com.maids.library_management_system.models.Book;
import com.maids.library_management_system.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(50);
        book.setIsbn("1234567890");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Test Author");
        bookDTO.setPublicationYear(50);
        bookDTO.setIsbn("1234567890");
    }

    @Test
    void getAllBooks_ShouldReturnBookList() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        var result = bookService.getAllBooks();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(bookDTO, result.get(0));
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(bookDTO, result.get());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void addBook_ShouldSaveAndReturnBook() {
        when(modelMapper.map(bookDTO, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        BookDTO result = bookService.addBook(bookDTO);

        assertNotNull(result);
        assertEquals(bookDTO, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_ShouldThrowException_WhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, bookDTO));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBook_ShouldThrowException_WhenBookNotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(anyLong());
    }
}
