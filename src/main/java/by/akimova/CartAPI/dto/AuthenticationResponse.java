package by.akimova.CartAPI.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwtToken;

    public AuthenticationResponse(String jwtToken) {

    }
}
