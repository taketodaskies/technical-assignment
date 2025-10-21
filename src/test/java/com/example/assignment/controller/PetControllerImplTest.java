package com.example.assignment.controller;

import com.example.assignment.data.dto.CreatePetDTO;
import com.example.assignment.data.dto.PatchPetDTO;
import com.example.assignment.data.model.Pet;
import com.example.assignment.repositories.PetsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PetControllerImplTest {

    public static final String BASE_PATH = "/pets";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockitoBean(reset = MockReset.BEFORE)
    private PetsRepository petsRepository;

    @Test
    @SneakyThrows
    void test_createPet_ok() {
        CreatePetDTO createPayload = CreatePetDTO.builder()
                .name("Happy path")
                .age(100) // Negative age
                .species("Use case") // Empty species
                .build();

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createPayload)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void test_createPet_badRequest() {
        CreatePetDTO malformedPayload = CreatePetDTO.builder()
                .name("Malformed")
                .age(-99) // Negative age
                .species("") // Empty species
                .build();

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(malformedPayload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors.length()").value(2));
    }

    @Test
    @SneakyThrows
    void test_getAllPets_ok() {
        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @ParameterizedTest(name = "Get pet by id - success")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_getPetById_ok(Long petId) {

        when(petsRepository.findById(petId)).thenReturn(Optional.of(Pet.builder().id(petId).build()));

        mockMvc.perform(get(BASE_PATH.concat("/{id}"), petId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petId));
    }

    @ParameterizedTest(name = "Get pet by id - not found")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_getPetById_notFound(Long petId) {
        mockMvc.perform(get(BASE_PATH.concat("/{id}"), petId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0]").value(containsString("Pet not found")));
    }

    @ParameterizedTest(name = "Patch pet by id - success")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_patchPetById_ok(Long petId) {

        when(petsRepository.findById(petId)).thenReturn(Optional.of(Pet.builder().id(petId).build()));

        PatchPetDTO patchPayload = PatchPetDTO.builder()
                .name("New name")
                .age(40)
                .ownerName("New owner")
                .build();

        mockMvc.perform(patch(BASE_PATH.concat("/{id}"), petId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(patchPayload)))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest(name = "Patch pet by id - not found")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_patchPetById_notFound(Long petId) {

        when(petsRepository.findById(petId)).thenReturn(Optional.empty());

        PatchPetDTO patchPayload = PatchPetDTO.builder()
                .name("New name")
                .age(40)
                .ownerName("New owner")
                .build();

        mockMvc.perform(patch(BASE_PATH.concat("/{id}"), petId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(patchPayload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0]").value(containsString("Pet not found")));
    }

    @ParameterizedTest(name = "Patch pet by id - bad request")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_patchPetById_badRequest(Long petId) {

        PatchPetDTO malformedPayload = PatchPetDTO.builder()
                .age(-40)
                .build();

        mockMvc.perform(patch(BASE_PATH.concat("/{id}"), petId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(malformedPayload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors.length()").value(1));
    }

    @ParameterizedTest(name = "Delete pet by id - success")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_deletePetById_ok(Long petId) {

        when(petsRepository.findById(petId)).thenReturn(Optional.of(Pet.builder().id(petId).build()));

        mockMvc.perform(delete(BASE_PATH.concat("/{id}"), petId))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest(name = "Delete pet by id - not found")
    @ValueSource(longs = { 1 })
    @SneakyThrows
    void test_deletePetById_notFound(Long petId) {

        when(petsRepository.findById(petId)).thenReturn(Optional.empty());

        mockMvc.perform(delete(BASE_PATH.concat("/{id}"), petId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0]").value(containsString("Pet not found")));
    }
}
