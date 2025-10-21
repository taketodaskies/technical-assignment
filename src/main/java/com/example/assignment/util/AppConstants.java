package com.example.assignment.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    // Database
    public static final String PETS_COLLECTION = "pets";
    public static final String OWNER_NAME_COLUMN_NAME = "owner_name";

    // Warnings
    public static final String NOT_BLANK_CONSTRAINT_MESSAGE = "must not be blank";
    public static final String POSITIVE_CONSTRAINT_MESSAGE = "Field must be positive";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation error on field [{0}]: {1}";
}
