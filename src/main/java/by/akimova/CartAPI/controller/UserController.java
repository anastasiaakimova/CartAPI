package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    /**
     * The method add new user to database.
     *
     * @param user This is parameters of user.
     * @return ResponseEntity with user and status created.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.findByMail(user.getMail()).get();/*orElseThrow(
                () -> new UsernameNotFoundException("User doesn't exists!")

        );*/
        if (savedUser != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * The method shows admin all users.
     *
     * @return ResponseEntity with list of users and status ok.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<?> findUserById(@PathVariable(value = "id") UUID id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

}
