package com.maids.library_management_system.dtos;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatronDTO {
    private Long id;

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 30, message = "name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "email  is required")
    @Email(message = "email must be a valid")
    private String email;
}