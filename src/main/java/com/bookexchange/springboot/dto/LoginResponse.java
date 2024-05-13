package com.bookexchange.springboot.dto;

public class LoginResponse {
    String token;
    final String type = "bearer";

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }
}
