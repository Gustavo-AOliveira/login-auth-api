package com.example.login_auth_api.service;

import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.exception.InvalidPasswordException;
import com.example.login_auth_api.exception.UserAlreadyExistsException;
import com.example.login_auth_api.exception.UserNotFoundException;
import com.example.login_auth_api.infra.security.TokenService;

import com.example.login_auth_api.exception.UserAlreadyExistsException;
import com.example.login_auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;


    public User saveUser(User user) {
        Optional<User> newUser = this.userRepository.findByEmail(user.getEmail());

        if (newUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exist");
        }
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public String authenticateUser(String email, String password) {
        User user = this.findUserByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Incorrect password, check your password.");
        }

        return tokenService.generateToken(user);
    }


}









