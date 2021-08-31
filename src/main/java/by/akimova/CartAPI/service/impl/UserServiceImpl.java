package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The class is implementation of user's business logic.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        log.info("IN register - user: {} successfully registered", user);
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());

        return result;
    }

    @Override
    public User updateUser(UUID id, User user) {
        User savedUser = userRepository.findById(id);

        savedUser.setName(user.getName());
        savedUser.setMail(user.getMail());
        savedUser.setPassword(user.getPassword());
        log.info("IN update - user with id: {} successfully updated", id);
        return userRepository.save(savedUser);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }

    @Override
    public Optional<User> findByMail(String mail) {
        log.info("IN findByMail - user found by mail: {}", mail);
        return Optional.ofNullable(userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists")));
    }
}
