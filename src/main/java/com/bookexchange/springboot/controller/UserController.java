package com.bookexchange.springboot.controller;

import com.bookexchange.springboot.dto.LoginRequest;
import com.bookexchange.springboot.dto.SignUpForm;
import com.bookexchange.springboot.entity.User;
import com.bookexchange.springboot.repository.UserRepository;
import com.bookexchange.springboot.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        String response = userService.login(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        userService.signUp(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam String userName) {
        Optional<User> user = userRepository.findByUsername(userName);

        if(user.isPresent()) {
            return ResponseEntity.ok("User found " + user.get());
        } else {
            return ResponseEntity.ok("user not found");
        }
    }
}