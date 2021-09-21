package by.akimova.CartAPI.service;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotAccessException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Cart;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for class {@link Cart}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface CartService {
    List<Cart> getAll();

    Cart getCartById(UUID id) throws EntityNotFoundException, NotValidUsernameException;

    Cart getCartByUserId(UUID userId) throws EntityNotFoundException, NotValidUsernameException;

    Cart deleteFromCart(UUID id, List<UUID> itemIds) throws EntityNotFoundException, NotValidUsernameException, NotAccessException;

    Cart saveCart(Cart cart);

    Cart updateCart(UUID id, Cart cart) throws EntityNotFoundException, NotValidUsernameException, NotAccessException;

    void deleteCartById(UUID id) throws EntityNotFoundException, NotAccessException;
}
