package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotAccessException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.CartRepository;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The class is implementation of cart's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * The method show all carts with all information about it.
     *
     * @return list of carts.
     */

    @Override
    public List<Cart> getAll() {
        List<Cart> carts = cartRepository.findAll();
        log.info("IN getAll - {} items found", carts.size());
        return carts;
    }

    /**
     * The method show cart with all information about it.
     *
     * @param cartId This is cart's id.
     * @return found cart.
     */
    @Override
    public Cart getCartById(UUID cartId) throws NotValidUsernameException, EntityNotFoundException {

        if (cartId == null) {
            log.error("IN getCartById - id is null ");
            throw new NotValidUsernameException("cartId is null");
        }

        Cart cart = cartRepository.findCartByCartId(cartId);

        if (cart == null) {
            log.error("IN getCartById - no cart found by id: {}", cartId);
            throw new EntityNotFoundException("Cart not found");
        }

        log.info("IN getCartById - cart: {} found by id: {}", cart, cartId);
        return cart;
    }

    /**
     * The method get cart by userId with all information about it.
     *
     * @param userId This is user's id.
     * @return found cart.
     */
    @Override
    public Cart getCartByUserId(UUID userId) throws EntityNotFoundException, NotValidUsernameException {

        if (userId == null) {
            log.error("IN getCartByUserId - id is null ");
            throw new NotValidUsernameException("cartId is null ");
        }

        Cart cart = cartRepository.findCartByUserId(userId);

        if (cart == null) {
            log.error("IN getCartByUserId - no cart found by userId: {}", userId);
            throw new EntityNotFoundException("Cart doesn't exist ");
        }

        log.info("IN getCartByUserId - cart: {} found by userId: {}", cart, userId);
        return cart;
    }

    /**
     * The method delete items from cart.
     *
     * @param cartId  This is cart's id which from needed to delete item.
     * @param itemIds This parameter consist items which needed delete.
     * @return cart without items which needed to be removed.
     */
    @Override
    public Cart deleteFromCart(UUID cartId, List<UUID> itemIds) throws EntityNotFoundException, NotValidUsernameException, NotAccessException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (cartId == null) {
            log.error("IN deleteFromCart - id is null ");
            throw new NotValidUsernameException("cartId is null");
        }

        Cart dbCart = cartRepository.findCartByCartId(cartId);

        if (dbCart == null) {
            log.error("IN deleteFromCart - no cart found by cartId: {}", cartId);
            throw new EntityNotFoundException("Cart doesn't exist ");
        }

        User user = userRepository.findUserByUserId(dbCart.getUserId());

        if (Objects.equals(username, user.getMail())) {
            Collection<Item> items = dbCart.getItems();
            Collection<Item> toDelete = new ArrayList<>();

            for (Item item : items) {
                if (itemIds.contains(item.getItemId()))
                    toDelete.add(item);
            }

            items.removeAll(toDelete);
        } else {
            log.error("IN deleteFromCart - This username can't delete items from this cart");
            throw new NotAccessException("Username is invalid");
        }

        log.info("IN deleteFromCart - cart with id: {} successfully delete {} items", cartId, itemIds.size());
        return cartRepository.save(dbCart);
    }

    /**
     * The method add new cart.
     *
     * @param cart This is cart with information about it, and it's fields
     * @return Saved cart.
     */
    @Override
    public Cart saveCart(Cart cart) {
        cart.setCartId(UUID.randomUUID());
        Collection<Item> items = cart.getItems();
        Collection<Item> cartsItem = new LinkedList<>();

        for (Item item : items) {
            Item savedItem = itemRepository.findItemByItemId(item.getItemId());
            cartsItem.add(savedItem);
        }

        cart.setItems(cartsItem);
        log.info("IN saveCart - new cart with id: {} successfully added", cart.getCartId());
        Cart savedCart = cartRepository.insert(cart);
        return savedCart;
    }

    /**
     * This method update cart.
     *
     * @param cartId This is cart's id which needed to update.
     * @param cart   This is updated cart.
     * @return Updated cart.
     */
    @Override
    public Cart updateCart(UUID cartId, Cart cart) throws NotValidUsernameException, EntityNotFoundException, NotAccessException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (cart == null) {
            log.error("IN updateCart - cart is null");
            throw new NotValidUsernameException("cart is null");
        }

        Cart dbCart = cartRepository.findCartByCartId(cartId);

        if (dbCart == null) {
            log.error("IN updateCart - no cart found by id: {}", cartId);
            throw new EntityNotFoundException("cart not found");
        }

        User user = userRepository.findUserByUserId(dbCart.getUserId());

        if (Objects.equals(username, user.getMail())) {
            Collection<Item> cartsItem = new LinkedList<>();

            if (cart.getItems() != null) {
                for (Item item : cart.getItems()) {
                    Item savedItem = itemRepository.findItemByItemId(item.getItemId());
                    cartsItem.add(savedItem);
                }
                dbCart.setItems(cartsItem);
            }

            log.info("IN updateCart - cart with id: {} successfully edited ", cartId);
        } else {
            log.error("IN updateCart - This username can't update this cart");
            throw new NotAccessException("Username is invalid");
        }
        return cartRepository.save(dbCart);
    }

    /**
     * This method delete cart.
     *
     * @param cartId This is cart's id which needed to delete.
     */
    @Override
    public void deleteCartById(UUID cartId) throws EntityNotFoundException, NotAccessException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Cart dbCart = cartRepository.findCartByCartId(cartId);

        if (dbCart == null) {
            log.error("IN updateCart - no cart found by id: {}", cartId);
            throw new EntityNotFoundException("cart not found");
        }

        User user = userRepository.findUserByUserId(dbCart.getUserId());

        if (Objects.equals(username, user.getMail())) {
            cartRepository.deleteByCartId(cartId);
            log.info("IN delete - cart with id: {} successfully deleted", cartId);
        } else {
            log.error("IN updateCart - This username can't update this cart");
            throw new NotAccessException("Username is invalid");
        }
    }
}
