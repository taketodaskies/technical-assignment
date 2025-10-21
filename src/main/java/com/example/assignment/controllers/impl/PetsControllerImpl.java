package com.example.assignment.controllers.impl;

import com.example.assignment.controllers.PetsController;
import com.example.assignment.data.dto.CreatePetDTO;
import com.example.assignment.data.dto.PatchPetDTO;
import com.example.assignment.data.dto.PetDTO;
import com.example.assignment.data.model.Pet;
import com.example.assignment.mappers.PetsMapper;
import com.example.assignment.services.PetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetsControllerImpl implements PetsController {

    private final PetsService petsService;
    private final PetsMapper petsMapper;

    @Override
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<Pet> pets = petsService.getPets();
        return ResponseEntity.ok(petsMapper.toDtoList(pets));
    }

    @Override
    public ResponseEntity<PetDTO> getPetById(Long id) {
        Pet pet = petsService.getPetById(id);
        return ResponseEntity.ok(petsMapper.toDto(pet));
    }

    @Override
    public ResponseEntity<PetDTO> createPet(CreatePetDTO createPayload) {

        Pet candidatePet = petsMapper.mapForCreate(createPayload);
        Pet newPet = petsService.createPet(candidatePet);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(petsMapper.toDto(newPet));
    }

    @Override
    public ResponseEntity<Void> patchPetById(Long id, PatchPetDTO patchPayload) {
        petsService.updatePet(id, patchPayload);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletePetById(Long id) {
        petsService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
