package com.bookexchange.springboot.service.implementation;

import com.bookexchange.springboot.configuration.JwtTokenProvider;
import com.bookexchange.springboot.dto.LoginResponse;
import com.bookexchange.springboot.entity.User;
import com.bookexchange.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UserService() {
    }

    public User signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginResponse login(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new LoginResponse(token);
    }

    public boolean isUsernameAvailable(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);

        if(user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    public boolean updateUser(User user) {
        Optional<User> usera = userRepository.findByUsername(user.getUsername());

        if(usera.isPresent()) {
            usera.get().setPassword(user.getPassword());
            userRepository.saveAndFlush(usera.get());
            return true;
        } else {
            return false;
        }
    }

}
