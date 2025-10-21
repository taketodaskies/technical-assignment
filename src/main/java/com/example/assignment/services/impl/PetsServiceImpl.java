package com.example.assignment.services.impl;

import com.example.assignment.data.dto.PatchPetDTO;
import com.example.assignment.data.model.Pet;
import com.example.assignment.exceptions.PetNotFoundException;
import com.example.assignment.repositories.PetsRepository;
import com.example.assignment.services.PetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PetsServiceImpl implements PetsService {

    private final PetsRepository petsRepository;

    @Override
    public List<Pet> getPets() {
        return petsRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petsRepository.findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    @Override
    public Pet createPet(Pet candidatePet) {
        return petsRepository.save(candidatePet);
    }

    @Override
    public void updatePet(Long id, PatchPetDTO updatePayload) {
        Pet pet = this.getPetById(id);
        handleNameUpdate(updatePayload, pet);
        handleAgeUpdate(updatePayload, pet);
        handleOwnerUpdate(updatePayload, pet);
        petsRepository.save(pet);
    }

    @Override
    public void deletePet(Long id) {
        Pet pet = this.getPetById(id);
        petsRepository.deleteById(pet.getId());
    }

    private static void handleOwnerUpdate(PatchPetDTO updatePayload, Pet pet) {
        Optional.ofNullable(updatePayload.getOwnerName())
                .ifPresent(pet::setOwnerName);
    }

    private static void handleNameUpdate(PatchPetDTO updatePayload, Pet pet) {
        Optional.ofNullable(updatePayload.getName())
                .ifPresent(pet::setName);
    }

    private static void handleAgeUpdate(PatchPetDTO updatePayload, Pet pet) {
        Optional.ofNullable(updatePayload.getAge())
                .ifPresent(pet::setAge);
    }
}
