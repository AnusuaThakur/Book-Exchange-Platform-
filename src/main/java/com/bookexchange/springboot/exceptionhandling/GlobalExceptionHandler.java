package com.bookexchange.springboot.exceptionhandling;


import com.bookexchange.springboot.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(Exception ex) {
        Response<String> response = new Response<>();
        response.setResponseCode(HttpStatus.UNAUTHORIZED);
        response.setErrorMessage("invalid username or password " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));
    }
}
