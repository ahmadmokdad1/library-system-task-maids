package com.maids.library_management_system.controllers;

import com.maids.library_management_system.dtos.PatronDTO;
import com.maids.library_management_system.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;
    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }
    @GetMapping
    public List<PatronDTO> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {
        return patronService.getPatronById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatronDTO> addPatron(@Valid @RequestBody PatronDTO patronDTO) {
        PatronDTO savedPatron = patronService.addPatron(patronDTO);
        return ResponseEntity.ok(savedPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDTO> updatePatron(
            @PathVariable Long id,
            @Valid @RequestBody PatronDTO patronDTO
    ) {
            PatronDTO updatedPatron = patronService.updatePatron(id, patronDTO);
            return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
            patronService.deletePatron(id);
            return ResponseEntity.noContent().build();
    }
}
