package by.akimova.CartAPI.service;

import by.akimova.CartAPI.exception.NotFoundEntityException;
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

    Cart getCartById(UUID id) throws NotFoundEntityException;

    Cart getCartByUserId(UUID userId) throws NotFoundEntityException;

    Cart deleteFromCart(UUID id, List<UUID> itemIds) throws NotFoundEntityException;

    Cart saveCart(Cart cart);

    Cart updateCart(UUID id, Cart cart) throws NotFoundEntityException;

    void deleteCartById(UUID id);
}
