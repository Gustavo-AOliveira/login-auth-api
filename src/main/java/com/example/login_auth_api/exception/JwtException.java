package com.example.login_auth_api.exception;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
