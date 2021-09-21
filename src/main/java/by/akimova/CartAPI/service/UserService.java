package by.akimova.CartAPI.service;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotFreeUsernameException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for class {@link User}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface UserService {

    User saveUser(User user) throws NotFreeUsernameException;

    User getById(UUID id) throws EntityNotFoundException, NotValidUsernameException;

    List<User> getAllUsers();

    User updateUser(UUID id, User user) throws EntityNotFoundException, NotValidUsernameException;

    void deleteUserById(UUID id);

    Optional<User> findByMail(String mail);

}
