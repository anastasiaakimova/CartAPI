package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.findByMail(user.getMail());
        if (savedUser != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
