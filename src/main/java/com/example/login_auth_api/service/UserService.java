package com.example.login_auth_api.service;

import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        Optional<User> newUser = userRepository.findByEmail(user.getEmail());

        if(newUser.isPresent()){
            throw new RuntimeException("User already exist");
        }
        return userRepository.save(user);

    }
}









