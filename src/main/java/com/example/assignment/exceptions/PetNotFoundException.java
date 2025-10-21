package com.example.assignment.exceptions;

import lombok.Getter;

@Getter
public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException() {
        super("Pet not found");
    }
}
