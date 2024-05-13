package com.bookexchange.springboot.controller;

import com.bookexchange.springboot.dto.LoginRequest;
import com.bookexchange.springboot.dto.LoginResponse;
import com.bookexchange.springboot.dto.Response;
import com.bookexchange.springboot.dto.SignUpForm;
import com.bookexchange.springboot.entity.User;
import com.bookexchange.springboot.repository.UserRepository;
import com.bookexchange.springboot.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
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
        LoginResponse loginResponse = userService.login(user);

        Response<LoginResponse> response = new Response<>();
        response.setResponseCode(HttpStatus.OK);
        response.setData(loginResponse);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getResponseCode()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpRequest) {

        Response<String> response = new Response();

        if(userService
                .isUsernameAvailable(signUpRequest.getUsername())) {
            response.setErrorMessage("User Name already taken");
            response.setResponseCode(HttpStatus.CONFLICT);

        } else {

            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setPassword(signUpRequest.getPassword());
            userService.signUp(user);
            response.setResponseCode(HttpStatus.CREATED);
            response.setData("User has been succesfully created");
        }
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getResponseCode()));
    }

    @GetMapping("/checkUsernameAvailablility")
    public ResponseEntity<?> getUsers(@RequestParam String userName) {
        Response<String> response = new Response<>();
        String message;
        if(userService.isUsernameAvailable(userName)) {
            message = "User Name already taken";
            response.setResponseCode(HttpStatus.CONFLICT);
            response.setData(message);
        } else {
            message = "User name is available";
            response.setData(message);
            response.setResponseCode(HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getResponseCode()));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Response<String> response = new Response<>();
        String message;

        if(userService.updateUser(user)) {
            message = "user details updated successfully";
            response.setData(message);
            response.setResponseCode(HttpStatus.OK);
        } else {
            response.setData("user details updation failed");
            response.setResponseCode(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getResponseCode()));
    }


}