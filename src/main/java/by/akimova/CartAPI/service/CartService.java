package by.akimova.CartAPI.service;

import by.akimova.CartAPI.model.Cart;

import java.util.UUID;

/**
 * The interface of business logic class
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface CartService {
    Cart showCart(UUID id);

    Cart deleteFromCart(UUID id, Cart cart);

    Cart saveCart(Cart cart);

    Cart updateCart(UUID id, Cart cart);

    void deleteCartById(UUID id);
}
