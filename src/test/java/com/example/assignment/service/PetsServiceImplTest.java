package com.example.assignment.service;

import com.example.assignment.data.dto.PatchPetDTO;
import com.example.assignment.data.model.Pet;
import com.example.assignment.exceptions.PetNotFoundException;
import com.example.assignment.repositories.PetsRepository;
import com.example.assignment.services.impl.PetsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetsServiceImplTest {

    @Mock
    private PetsRepository petsRepository;

    @Captor
    private ArgumentCaptor<Long> petIdArgumentCaptor;

    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;

    @InjectMocks
    private PetsServiceImpl petsService;

    private final List<Pet> mockedPets = List.of(
            Pet.builder().name("Bob").age(1).species("Dog").build(),
            Pet.builder().name("Sam").age(2).species("Cat").build(),
            Pet.builder().name("Luna").age(3).species("Rabbit").build()
    );

    // getPets method
    @Test
    void test_getPets_ok() {
        // Stub
        when(petsRepository.findAll()).thenReturn(mockedPets);
        // Execute
        List<Pet> retrievedPets = petsService.getPets();
        // Assert
        assertThat(retrievedPets).hasSize(3);
        assertThat(retrievedPets).usingRecursiveComparison().isEqualTo(mockedPets);
    }


    // getPetById method
    @Test
    void test_getPetById_ok() {
        when(petsRepository.findById(petIdArgumentCaptor.capture())).thenReturn(Optional.of(mockedPets.getFirst()));

        Pet retrievedPet = petsService.getPetById(1L);
        verify(petsRepository, times(1)).findById(petIdArgumentCaptor.getValue());
        assertThat(retrievedPet).usingRecursiveComparison().isEqualTo(mockedPets.getFirst());
    }

    @Test
    void test_getPetById_notFound() {
        when(petsRepository.findById(petIdArgumentCaptor.capture())).thenReturn(Optional.empty());

        assertThrows(
                PetNotFoundException.class,
                () -> petsService.getPetById(1L)
        );
        verify(petsRepository, times(1)).findById(petIdArgumentCaptor.getValue());
        verifyNoMoreInteractions(petsRepository);
    }


    // deletePet method
    @Test
    void test_deletePet_ok() {
        when(petsRepository.findById(petIdArgumentCaptor.capture())).thenReturn(Optional.of(mockedPets.getFirst()));
        doNothing().when(petsRepository).deleteById(petIdArgumentCaptor.capture());
        assertDoesNotThrow(() -> petsService.deletePet(1L));

        verify(petsRepository, times(1)).findById(petIdArgumentCaptor.getAllValues().getFirst());
        verify(petsRepository, times(1)).deleteById(petIdArgumentCaptor.getAllValues().getLast());
        verifyNoMoreInteractions(petsRepository);
    }

    @Test
    void test_deletePet_notFound() {

        when(petsRepository.findById(petIdArgumentCaptor.capture())).thenReturn(Optional.empty());

        assertThrows(
                PetNotFoundException.class,
                () -> petsService.deletePet(1L)
        );

        verify(petsRepository, times(1)).findById(petIdArgumentCaptor.getValue());
        verify(petsRepository, never()).deleteById(petIdArgumentCaptor.getValue());
        verifyNoMoreInteractions(petsRepository);
    }

    // updatePet method
    @Test
    void test_updatePet_ok() {

        when(petsRepository.findById(petIdArgumentCaptor.capture())).thenReturn(Optional.of(mockedPets.getFirst()));
        when(petsRepository.save(petArgumentCaptor.capture())).thenReturn(mockedPets.getFirst());

        PatchPetDTO mockedPatchPayload = PatchPetDTO.builder()
                .name("Updated name")
                .ownerName("Updated owner")
                .age(99)
                .build();

        petsService.updatePet(1L, mockedPatchPayload);

        Pet capturedPet = petArgumentCaptor.getValue();

        assertEquals(mockedPatchPayload.getName(), capturedPet.getName());
        assertEquals(mockedPatchPayload.getOwnerName(), capturedPet.getOwnerName());
        assertEquals(mockedPatchPayload.getAge(), capturedPet.getAge());

        verify(petsRepository, times(1)).findById(petIdArgumentCaptor.getValue());
        verify(petsRepository, times(1)).save(capturedPet);
        verifyNoMoreInteractions(petsRepository);
    }

    @Test
    void test_updatePet_notFound() {

        when(petsRepository.findById(petIdArgumentCaptor.capture())).thenReturn(Optional.empty());

        PatchPetDTO mockedPatchPayload = PatchPetDTO.builder()
                .name("Updated name")
                .ownerName("Updated owner")
                .age(99)
                .build();

        assertThrows(
                PetNotFoundException.class,
                () -> petsService.updatePet(1L, mockedPatchPayload)
        );

        verify(petsRepository, times(1)).findById(petIdArgumentCaptor.getValue());
        verify(petsRepository, never()).save(any(Pet.class));
        verifyNoMoreInteractions(petsRepository);
    }

    // createPet method
    @Test
    void test_createPet_ok() {
        when(petsRepository.save(petArgumentCaptor.capture())).thenReturn(mockedPets.getFirst());

        Pet createdPet = petsService.createPet(mockedPets.getFirst());

        Pet capturedPet = petArgumentCaptor.getValue();
        assertThat(createdPet).usingRecursiveComparison().isEqualTo(capturedPet);
        verify(petsRepository, times(1)).save(capturedPet);
    }
}
