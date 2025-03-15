package com.example.login_auth_api.dto.exception;

import org.springframework.validation.FieldError;

public record ErrorValidationDTO(String field, String message) {

    public ErrorValidationDTO(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
