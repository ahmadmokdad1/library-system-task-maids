package com.maids.library_management_system.controllers;

import com.maids.library_management_system.dtos.BorrowingRecordDTO;
import com.maids.library_management_system.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId
    ) {

        BorrowingRecordDTO borrowingRecordDTO = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecordDTO);

    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {

        BorrowingRecordDTO borrowingRecordDTO = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok(borrowingRecordDTO);

    }

    @GetMapping("/borrowing-records")
    public List<BorrowingRecordDTO> getAllBorrowingRecords() {
        return borrowingRecordService.getAllBorrowingRecords();
    }
}
