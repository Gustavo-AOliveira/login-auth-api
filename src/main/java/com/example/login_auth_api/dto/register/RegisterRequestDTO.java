package com.example.login_auth_api.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String password
) {
}
