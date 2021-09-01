package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller user connected requests.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * The method shows all users.
     *
     * @return ResponseEntity with list of users and status ok.
     */
    @GetMapping
    ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
