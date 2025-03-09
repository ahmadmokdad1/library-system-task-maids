package com.maids.library_management_system.dtos;


import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "title is required")
    @Size(min = 2, max = 100, message = "title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "author is required")
    @Size(min = 2, max = 50, message = "author must be between 2 and 50 characters")
    private String author;

    @Min(value = 1500, message = "publication year must be after 1500")
//    @Max(value = 2025, message = "publication year cannot be in the future")
    private int publicationYear;

    @NotBlank(message = "isbn is required")
    @Pattern(regexp = "^(?:isbn(?:-13)?:? )?(?=[0-9]{13}$|[0-9X]{10}$)(?:97[89][ -]?)?[0-9]{1,5}[ -]?[0-9]+[ -]?[0-9]+[ -]?[0-9X]$",
            message = "invalid isbn format")
    private String isbn;
}
