package com.example.login_auth_api.dto.login;

public record LoginResponseDTO(
        String email,
        String token
) {
}
