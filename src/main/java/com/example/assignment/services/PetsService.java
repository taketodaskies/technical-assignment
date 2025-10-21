package com.example.assignment.services;

import com.example.assignment.data.dto.PatchPetDTO;
import com.example.assignment.data.model.Pet;

import java.util.List;

public interface PetsService {
    List<Pet> getPets();
    Pet getPetById(Long id);
    Pet createPet(Pet candidatePet);
    void updatePet(Long id, PatchPetDTO updatePayload);
    void deletePet(Long id);
}
