package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.CartRepository;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * The class is implementation of cart's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;


    public CartServiceImpl(@Qualifier("cartRepository") CartRepository cartRepository, @Qualifier("itemRepository") ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * The method show all carts with all information about it.
     *
     * @return list of carts.
     */

    @Override
    public List<Cart> getAll() {
        List<Cart> carts = cartRepository.findAll();

        for (Cart cart : carts) {
            Collection<Item> cartsItem = new LinkedList<>();
            Collection<Item> items = cart.getItems();
            for (Item item : items) {
                UUID id = item.getItemId();
                Item savedItem = itemRepository.findItemByItemId(id);

                item.set_id(savedItem.get_id());
                item.setName(savedItem.getName());
                item.setModel(savedItem.getModel());
                item.setBrand(savedItem.getBrand());
                item.setYear(savedItem.getYear());
                cartsItem.add(item);
            }
            cart.getItems().remove(items);
            cart.setItems(cartsItem);

        }
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
    public Cart getCartById(UUID cartId) {
        Cart cart = cartRepository.findCartByCartId(cartId);

        if (cart == null) {
            log.warn("IN getCartById - no cart found by id: {}", cartId);
            return null;
        }
        Collection<Item> cartsItem = new LinkedList<>();
        Collection<Item> items = cart.getItems();
        for (Item item : items) {
            UUID id = item.getItemId();
            Item savedItem = itemRepository.findItemByItemId(id);

            item.set_id(savedItem.get_id());
            item.setName(savedItem.getName());
            item.setModel(savedItem.getModel());
            item.setBrand(savedItem.getBrand());
            item.setYear(savedItem.getYear());
            cartsItem.add(item);
        }
        cart.getItems().remove(items);
        cart.setItems(cartsItem);

        log.info("IN getCartById - cart: {} found by id: {}", cart, cartId);

        return cart;
    }

    /**
     * The method delete items from cart.
     *
     * @param cartId This is cart's id which from needed to delete item.
     * @param cart   This parameter consist items which needed delete.
     * @return cart without items which needed to be removed.
     */
    @Override
    public Cart deleteFromCart(UUID cartId, Cart cart) {
        Cart savedCart = cartRepository.findCartByCartId(cartId);
        if (savedCart == null) {
            log.warn("IN deleteFromCart - no cart found by id: {}", cartId);
            return null;
        }
        Collection<Item> deleteItems = cart.getItems();
        Collection<Item> items = savedCart.getItems();
        Collection<Item> cartsItem = new LinkedList<>();

        for (Item item : items) {
            UUID id = item.getItemId();
            Item savedItem = itemRepository.findItemByItemId(id);
            item.set_id(savedItem.get_id());
            item.setName(savedItem.getName());
            item.setModel(savedItem.getModel());
            item.setBrand(savedItem.getBrand());
            item.setYear(savedItem.getYear());
            cartsItem.add(item);
        }
        for (Item item : deleteItems) {
            UUID id = item.getItemId();
            Item savedItem = itemRepository.findItemByItemId(id);
            item.set_id(savedItem.get_id());
            item.setName(savedItem.getName());
            item.setModel(savedItem.getModel());
            item.setBrand(savedItem.getBrand());
            item.setYear(savedItem.getYear());
            cartsItem.remove(item);

        }
        savedCart.setItems(cartsItem);
        log.info("IN deleteFromCart - cart with id: {} successfully delete items {}", cartId, cart.getItems().size());
        return cartRepository.save(savedCart);
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
            UUID id = item.getItemId();
            Item savedItem = itemRepository.findItemByItemId(id);
            item.set_id(savedItem.get_id());
            item.setName(savedItem.getName());
            item.setModel(savedItem.getModel());
            item.setBrand(savedItem.getBrand());
            item.setYear(savedItem.getYear());
            cartsItem.add(item);
        }
        cart.getItems().remove(items);
        cart.setItems(cartsItem);

        log.info("IN saveCart - new cart with id: {} successfully added", cart.getCartId());
        return cartRepository.insert(cart);
    }

    /**
     * This method update cart.
     *
     * @param cartId This is cart's id which needed to update.
     * @param cart   This is updated cart.
     * @return Updated cart.
     */
    @Override
    public Cart updateCart(UUID cartId, Cart cart) {
        Cart savedCart = cartRepository.findCartByCartId(cartId);
        if (savedCart == null) {
            log.warn("IN updateCart - no item found by id: {}", cartId);
            return null;
        }

        Collection<Item> newItems = cart.getItems();
        Collection<Item> items = savedCart.getItems();
        Collection<Item> cartsItem = new LinkedList<>();
        for (Item item : items) {
            UUID id = item.getItemId();
            Item savedItem = itemRepository.findItemByItemId(id);
            item.set_id(savedItem.get_id());
            item.setName(savedItem.getName());
            item.setModel(savedItem.getModel());
            item.setBrand(savedItem.getBrand());
            item.setYear(savedItem.getYear());
            cartsItem.add(item);
        }
        savedCart.getItems().remove(items);
        for (Item item : newItems) {
            UUID id = item.getItemId();
            Item savedItem = itemRepository.findItemByItemId(id);
            item.set_id(savedItem.get_id());
            item.setName(savedItem.getName());
            item.setModel(savedItem.getModel());
            item.setBrand(savedItem.getBrand());
            item.setYear(savedItem.getYear());
            cartsItem.add(item);
        }
        savedCart.setItems(cartsItem);

        log.info("IN updateCart - cart with id: {} successfully edited ", cartId);
        return cartRepository.save(savedCart);
    }

    /**
     * This method delete cart.
     *
     * @param cartId This is cart's id which needed to delete.
     */
    @Override
    public void deleteCartById(UUID cartId) {
        cartRepository.deleteByCartId(cartId);
        log.info("IN delete - cart with id: {} successfully deleted", cartId);
    }
}
