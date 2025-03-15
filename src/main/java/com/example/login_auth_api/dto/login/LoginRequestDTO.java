package com.example.login_auth_api.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
        ) {
}
