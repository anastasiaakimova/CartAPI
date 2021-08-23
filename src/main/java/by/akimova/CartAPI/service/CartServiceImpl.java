package by.akimova.CartAPI.service;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The class is implementation of cart's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * The method show cart with all information about it.
     *
     * @param id This is cart's id.
     * @return found cart.
     */
    @Override
    public Cart showCart(UUID id) {
        return cartRepository.findById(id);
    }

    /**
     * The method delete items from cart.
     *
     * @param id   This is cart's id which from needed to delete item.
     * @param cart This parameter consist items which needed delete.
     * @return cart without items which needed to be removed.
     */
    @Override
    public Cart deleteFromCart(UUID id, Cart cart) {
        Cart savedCart = cartRepository.findById(id);
        savedCart.getItems().remove(cart.getItems());
        return savedCart;
    }

    /**
     * The method add new cart.
     *
     * @param cart This is cart with information about it, and it's fields
     * @return Saved cart.
     */
    @Override
    public Cart saveCart(Cart cart) {
        cart.setId(UUID.randomUUID());
        return cartRepository.insert(cart);
    }

    /**
     * This method update cart.
     *
     * @param id   This is cart's id which needed to update.
     * @param cart This is updated cart.
     * @return Updated cart.
     */
    @Override
    public Cart updateCart(UUID id, Cart cart) {
        Cart savedCart = cartRepository.findById(id);

        savedCart.setItems(cart.getItems());
        savedCart.setNumOfItems(cart.getNumOfItems());

        return cartRepository.save(savedCart);
    }

    /**
     * This method delete cart.
     *
     * @param id This is cart's id which needed to delete.
     */
    @Override
    public void deleteCartById(UUID id) {
        cartRepository.deleteById(id);
    }
}
