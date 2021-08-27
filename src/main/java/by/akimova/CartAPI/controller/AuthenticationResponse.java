package by.akimova.CartAPI.controller;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwtToken;

    public AuthenticationResponse(String jwtToken) {

    }
}
