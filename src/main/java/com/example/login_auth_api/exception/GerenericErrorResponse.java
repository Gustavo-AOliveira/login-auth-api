package com.example.login_auth_api.exception;

public class GerenericErrorResponse extends RuntimeException {
    public GerenericErrorResponse(String message, Throwable cause) {
        super(message, cause);
    }
}
