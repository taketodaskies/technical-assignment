package com.example.assignment.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import static com.example.assignment.util.AppConstants.NOT_BLANK_CONSTRAINT_MESSAGE;
import static com.example.assignment.util.AppConstants.POSITIVE_CONSTRAINT_MESSAGE;

@Data
@Builder
public class CreatePetDTO {
    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    private String name;

    @Min(value = 0, message = POSITIVE_CONSTRAINT_MESSAGE)
    private int age;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    private String species;

    private String ownerName;

}
