package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.CartRepository;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
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
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
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
        user.setPassword(user.getPassword());
        user.setRole(user.getRole());
        user.setCarts(user.getCarts());
        if (user.getCarts() != null) {
            for (Cart cart : user.getCarts()) {
                Cart savedCart = cartRepository.findCartByCartId(cart.getCartId());
                cart.setCartId(savedCart.getCartId());
                cart.set_id(savedCart.get_id());
                cart.setItems(savedCart.getItems());
                if (cart.getItems() != null) {
                    for (Item item : cart.getItems()) {
                        Item savedItem = itemRepository.findItemByItemId(item.getItemId());
                        item.set_id(savedItem.get_id());
                        item.setItemId(savedItem.getItemId());
                        item.setName(savedItem.getName());
                        item.setModel(savedItem.getModel());
                        item.setBrand(savedItem.getBrand());
                        item.setYear(savedItem.getYear());
                    }
                }
            }
        }

        log.info("IN saveUser - new user with id: {} successfully added", user.getUserId());
        return userRepository.save(user);
    }

    /**
     * The method show user with all information about it.
     *
     * @param userId This is user's id.
     * @return found user.
     */
    @Override
    public User findById(UUID userId) {
        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            log.warn("IN findById - no user found by id: {}", user);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", user, userId);
        return user;
    }

    /**
     * The method show all users with all information about it.
     *
     * @return list of users.
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            User savedUser = userRepository.findUserByUserId(user.getUserId());
            user.setUserId(savedUser.getUserId());
            user.set_id(savedUser.get_id());
            user.setName(savedUser.getName());
            user.setMail(savedUser.getMail());
            user.setPassword(savedUser.getPassword());
            user.setRole(savedUser.getRole());
            user.setCarts(savedUser.getCarts());
            if (user.getCarts() != null) {
                for (Cart cart : user.getCarts()) {
                    Cart savedCart = cartRepository.findCartByCartId(cart.getCartId());
                    cart.setCartId(savedCart.getCartId());
                    cart.set_id(savedCart.get_id());
                    cart.setItems(savedCart.getItems());
                    if (cart.getItems() != null) {
                        for (Item item : cart.getItems()) {
                            Item savedItem = itemRepository.findItemByItemId(item.getItemId());
                            item.set_id(savedItem.get_id());
                            item.setItemId(savedItem.getItemId());
                            item.setName(savedItem.getName());
                            item.setModel(savedItem.getModel());
                            item.setBrand(savedItem.getBrand());
                            item.setYear(savedItem.getYear());
                        }
                    }
                }
            }
        }
        log.info("IN getAllUsers - {} users found", users.size());
        return users;
    }

    /**
     * This method update user.
     *
     * @param userId This is user's id which needed to update.
     * @param user   This is updated user.
     * @return Updated user.
     */
    @Override
    public User updateUser(UUID userId, User user) {
        User savedUser = userRepository.findUserByUserId(userId);

        if (savedUser == null) {
            log.warn("IN updateUser - no user found by id: {}", savedUser);
            return null;
        }
        savedUser.setName(user.getName());
        savedUser.setMail(user.getMail());
        savedUser.setPassword(user.getPassword());
        savedUser.setRole(user.getRole());
        savedUser.setCarts(user.getCarts());
        if (savedUser.getCarts() != null) {
            for (Cart cart : savedUser.getCarts()) {
                Cart savedCart = cartRepository.findCartByCartId(cart.getCartId());
                cart.setCartId(savedCart.getCartId());
                cart.set_id(savedCart.get_id());
                cart.setItems(savedCart.getItems());
                if (cart.getItems() != null) {
                    for (Item item : cart.getItems()) {
                        Item savedItem = itemRepository.findItemByItemId(item.getItemId());
                        item.set_id(savedItem.get_id());
                        item.setItemId(savedItem.getItemId());
                        item.setName(savedItem.getName());
                        item.setModel(savedItem.getModel());
                        item.setBrand(savedItem.getBrand());
                        item.setYear(savedItem.getYear());
                    }
                }
            }
        }
        log.info("IN updateUser - user with id: {} successfully edited ", userId);
        return userRepository.save(savedUser);
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
