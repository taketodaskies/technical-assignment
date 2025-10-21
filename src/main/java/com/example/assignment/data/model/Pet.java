package com.example.assignment.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.assignment.util.AppConstants.*;

@Data
@Entity(name = PETS_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    private String name;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    private String species;

    @Min(value = 0, message = POSITIVE_CONSTRAINT_MESSAGE)
    private int age;

    @Column(name = OWNER_NAME_COLUMN_NAME)
    private String ownerName;
}
