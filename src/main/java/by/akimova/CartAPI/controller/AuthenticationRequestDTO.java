package by.akimova.CartAPI.controller;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String mail;
    private String password;
}
