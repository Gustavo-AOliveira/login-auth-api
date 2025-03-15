package com.example.login_auth_api.controller;

import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.dto.login.LoginRequestDTO;
import com.example.login_auth_api.dto.login.LoginResponseDTO;
import com.example.login_auth_api.dto.register.RegisterRequestDTO;
import com.example.login_auth_api.dto.register.RegisterResponseDTO;
import com.example.login_auth_api.exception.InvalidPasswordException;
import com.example.login_auth_api.exception.UserNotFoundException;
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

import java.util.List;
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


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO body) {
            String token = userService.authenticateUser(body.email(), body.password());
                return ResponseEntity.ok(new LoginResponseDTO(body.email(), token));
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO body) {

            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            userService.saveUser(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new RegisterResponseDTO(newUser.getName(), token));
        }
        @GetMapping
        public ResponseEntity<List<User>> getUser(){
            List<User> userList = userRepository.findAll();
                return ResponseEntity.status(HttpStatus.OK).body(userList);

    }
}










