package com.maids.library_management_system.dtos;


import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BorrowingRecordDTO {
    private Long id;

    @NotNull(message = "book id is required")
    private Long bookId;

    @NotNull(message = "patron id is required")
    private Long patronId;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
}