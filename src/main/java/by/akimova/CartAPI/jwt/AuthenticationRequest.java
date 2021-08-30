package by.akimova.CartAPI.jwt;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String mail;
    private String password;
}
