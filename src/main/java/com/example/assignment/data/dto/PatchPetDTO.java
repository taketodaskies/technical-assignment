package com.example.assignment.data.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import static com.example.assignment.util.AppConstants.POSITIVE_CONSTRAINT_MESSAGE;

@Data
@Builder
public class PatchPetDTO {
    private String name;

    @Min(value = 0, message = POSITIVE_CONSTRAINT_MESSAGE)
    private Integer age;

    private String ownerName;
}
