package com.maids.library_management_system.services;


import com.maids.library_management_system.dtos.PatronDTO;
import com.maids.library_management_system.exceptions.ResourceNotFoundException;
import com.maids.library_management_system.models.Patron;
import com.maids.library_management_system.repositories.PatronRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;


    private final ModelMapper modelMapper;

    public PatronServiceImpl(PatronRepository patronRepository, ModelMapper modelMapper) {
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll()
                .stream()
                .map(patron -> modelMapper.map(patron, PatronDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PatronDTO> getPatronById(Long id) {
        return patronRepository.findById(id)
                .map(patron -> modelMapper.map(patron, PatronDTO.class));
    }

    @Override
    @Transactional
    public PatronDTO addPatron(PatronDTO patronDTO) {
        Patron patron = modelMapper.map(patronDTO, Patron.class);
        Patron savedPatron = patronRepository.save(patron);
        return modelMapper.map(savedPatron, PatronDTO.class);
    }

    @Override
    @Transactional
    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("patron with id " + id + " not found"));
        patron.setName(patronDTO.getName());
        patron.setEmail(patronDTO.getEmail());
        Patron updatedPatron = patronRepository.save(patron);
        return modelMapper.map(updatedPatron, PatronDTO.class);
    }

    @Override
    @Transactional
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new ResourceNotFoundException("patron with id " + id + " not found");
        }
        patronRepository.deleteById(id);
    }
}