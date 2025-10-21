package com.example.assignment.exceptions;

import com.example.assignment.data.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.List;

import static com.example.assignment.util.AppConstants.VALIDATION_ERROR_MESSAGE;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ApiError> handlePetNotFoundException(PetNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getApiError(List.of(ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(fieldErr -> MessageFormat.format(
                        VALIDATION_ERROR_MESSAGE,
                        fieldErr.getField(),
                        fieldErr.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getApiError(errors));
    }

    private ApiError getApiError(List<String> errors) {
        return ApiError.builder()
                .errors(errors)
                .build();
    }
}
