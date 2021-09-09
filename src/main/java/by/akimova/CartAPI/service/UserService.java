package by.akimova.CartAPI.service;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.ValidationException;
import by.akimova.CartAPI.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for class {@link User}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

public interface UserService {

    User saveUser(User user);

    User getById(UUID id) throws EntityNotFoundException, ValidationException;

    List<User> getAllUsers();

    User updateUser(UUID id, User user) throws EntityNotFoundException, ValidationException;

    void deleteUserById(UUID id);

}
