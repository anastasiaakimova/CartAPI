package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.ValidationException;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The class is implementation of  {@link UserService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * The method show all users with all information about it.
     *
     * @return list of users.
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("IN getAllUsers - {} users found", users.size());
        return users;
    }

    /**
     * The method show user with all information about it.
     *
     * @param userId This is user's id.
     * @return found user.
     */
    @Override
    public User getById(UUID userId) throws EntityNotFoundException, ValidationException {
        if (userId == null) {
            log.error("IN getById - userId is null ");
            throw new ValidationException("userId is null");
        }
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            log.error("IN getById -  no user found by id {}", userId);
            throw new EntityNotFoundException("user not found");
        }
        log.info("IN getById - user: {} found by id: {}", user, userId);
        return user;
    }

    /**
     * The method add new user.
     *
     * @param user This is user with information about it, and it's fields
     * @return Saved user.
     */

    @Override
    public User saveUser(User user) {
        user.setUserId(UUID.randomUUID());
        log.info("IN saveUser - new user with id: {} successfully added", user.getUserId());
        return userRepository.save(user);
    }


    /**
     * This method update user.
     *
     * @param userId This is user's id which needed to update.
     * @param user   This is updated user.
     * @return Updated user.
     */
    @Override
    public User updateUser(UUID userId, User user) throws EntityNotFoundException, ValidationException {
        if (user == null) {
            log.error("IN updateUser - user is null");
            throw new ValidationException("user is null");
        }
        User dbUser = userRepository.findUserByUserId(userId);
        if (dbUser == null) {
            log.error("IN updateUser - user not found by id {}", userId);
            throw new EntityNotFoundException("user not found");
        }
        dbUser.setName(user.getName());
        dbUser.setMail(user.getMail());
        dbUser.setPassword(user.getPassword());
        dbUser.setRole(user.getRole());

        log.info("IN updateUser - user with id: {} successfully edited ", userId);
        return userRepository.save(dbUser);
    }

    /**
     * This method delete user.
     *
     * @param userId This is user's id which needed to delete.
     */
    @Override
    public void deleteUserById(UUID userId) {
        userRepository.deleteUserByUserId(userId);
        log.info("IN deleteUserById - user with id: {} successfully deleted", userId);
    }
}
