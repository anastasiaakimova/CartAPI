package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
    }
}
