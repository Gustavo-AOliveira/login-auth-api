package com.example.login_auth_api.controller;

import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.dto.login.LoginRequestDTO;
import com.example.login_auth_api.dto.login.LoginResponseDTO;
import com.example.login_auth_api.dto.register.RegisterRequestDTO;
import com.example.login_auth_api.dto.register.RegisterResponseDTO;
import com.example.login_auth_api.infra.security.TokenService;
import com.example.login_auth_api.repository.UserRepository;
import com.example.login_auth_api.service.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @GetMapping
    public String getUser(){
        return "Sucesso";
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO body) {
        try {
            User user = userRepository.findByEmail(body.email())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (passwordEncoder.matches(body.password(), user.getPassword())) {
                String token = tokenService.generateToken(user);
                return ResponseEntity.ok(new LoginResponseDTO(user.getName(), token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password, check your password.");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO body) {
        try {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            userService.saveUser(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new RegisterResponseDTO(newUser.getName(), token));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error by registering user: " + ex.getMessage());
        }
    }

}









