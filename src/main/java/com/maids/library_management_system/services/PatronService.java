package com.maids.library_management_system.services;

import com.maids.library_management_system.dtos.PatronDTO;

import java.util.List;
import java.util.Optional;

public interface PatronService {
    List<PatronDTO> getAllPatrons();
    Optional<PatronDTO> getPatronById(Long id);
    PatronDTO addPatron(PatronDTO patronDTO);
    PatronDTO updatePatron(Long id, PatronDTO patronDTO);
    void deletePatron(Long id);
}
