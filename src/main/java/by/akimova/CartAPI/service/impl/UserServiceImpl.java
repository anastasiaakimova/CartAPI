package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The class is implementation of user's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UUID id, User user) {
        User savedUser = userRepository.findById(id);

        savedUser.setName(user.getName());
        savedUser.setMail(user.getMail());
        savedUser.setPassword(user.getPassword());

        return userRepository.save(savedUser);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByMail(String mail) {
        return userRepository.findByMail(mail);
    }
}
