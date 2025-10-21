package com.example.assignment.mappers;

import com.example.assignment.data.dto.CreatePetDTO;
import com.example.assignment.data.dto.PetDTO;
import com.example.assignment.data.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetsMapper {
    @Mapping(source = "id", target = "id")
    PetDTO toDto(Pet pet);

    List<PetDTO> toDtoList(List<Pet> entityList);

    Pet mapForCreate(CreatePetDTO dto);
}
