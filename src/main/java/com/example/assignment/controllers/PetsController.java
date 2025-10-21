package com.example.assignment.controllers;

import com.example.assignment.data.dto.CreatePetDTO;
import com.example.assignment.data.dto.PatchPetDTO;
import com.example.assignment.data.dto.PetDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pets Controller", description = "Use this controller to perform CRUD operations on Pet entities")
@RequestMapping("pets")
public interface PetsController {

    @GetMapping
    ResponseEntity<List<PetDTO>> getAllPets();

    @GetMapping("/{id}")
    ResponseEntity<PetDTO> getPetById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<PetDTO> createPet(@Valid @RequestBody CreatePetDTO createPayload);

    @PatchMapping("/{id}")
    ResponseEntity<Void> patchPetById(@PathVariable Long id, @Valid @RequestBody PatchPetDTO patchPayload);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePetById(@PathVariable Long id);
}
