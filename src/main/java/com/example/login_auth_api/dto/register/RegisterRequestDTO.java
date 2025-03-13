package com.example.login_auth_api.dto.register;

public record RegisterRequestDTO(
        String name,
        String email,
        String password
) {
}
