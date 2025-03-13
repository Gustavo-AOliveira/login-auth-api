package com.example.login_auth_api.dto.login;

public record LoginRequestDTO(
        String email,
        String password
        ) {
}
