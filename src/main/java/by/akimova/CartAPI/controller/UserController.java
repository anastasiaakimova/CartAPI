package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller user connected requests.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * The method shows all users.
     *
     * @return ResponseEntity with list of users and status ok.
     */
    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * The method shows user by id.
     *
     * @param id This is user's id which should be viewed.
     * @return ResponseEntity with body of user and status ok.
     */

    @GetMapping("/{id}")
    ResponseEntity<?> getUserById(@PathVariable(value = "id") UUID id) {
        User user;
        try {
            user = userService.getById(id);
        } catch (NullPointerException e) {
            log.error("In UserController getUserById - id doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * The method add new user.
     *
     * @param user This is item with its information and body.
     * @return response with body of created user and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    /**
     * The method update item.
     *
     * @param id   This is user's id which should be updated.
     * @param user This is new body for user which should be updated.
     * @return response with body of updated user and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") UUID id, @RequestBody User user) {
        User updatedUser;
        try {
            updatedUser = userService.updateUser(id, user);
        } catch (NullPointerException e) {
            log.error("In UserController updateUser - user by id {} is null", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * The method delete user.
     *
     * @param id This is user's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") UUID id) {
        try {
            userService.deleteUserById(id);
        }catch (NullPointerException e) {
            log.error("In UserController deleteUser - cart by id {} is not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
