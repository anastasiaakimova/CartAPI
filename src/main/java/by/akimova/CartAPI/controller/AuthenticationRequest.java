package by.akimova.CartAPI.controller;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String mail;
    private String password;
}
