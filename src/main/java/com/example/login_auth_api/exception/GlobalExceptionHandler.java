package com.example.login_auth_api.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<GerenericErrorResponse> userAlreadyExistsException(UserAlreadyExistsException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidationDTO>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorValidationDTO> errors = ex.getFieldErrors().stream().map(ErrorValidationDTO::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<GerenericErrorResponse> invalidPasswordException(InvalidPasswordException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<GerenericErrorResponse> handleJwtException(JwtException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GerenericErrorResponse> authenticationException(AuthenticationException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GerenericErrorResponse> usernameNotFoundException(UsernameNotFoundException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GerenericErrorResponse> userNotFoundException(UserNotFoundException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GerenericErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        GerenericErrorResponse error = new GerenericErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private record ErrorValidationDTO(String field, String message) {

        private ErrorValidationDTO(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
