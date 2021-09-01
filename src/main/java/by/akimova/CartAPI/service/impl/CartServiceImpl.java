package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.CartRepository;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.CartService;
import by.akimova.CartAPI.service.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The class is implementation of cart's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public CartServiceImpl(@Qualifier("cartRepository") CartRepository cartRepository, @Qualifier("itemRepository") ItemRepository itemRepository, ItemService itemService) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
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
                System.out.println(item);;
                Item savedItem = itemService.getById(id);
      //          savedItem.setId(id);
                System.out.println(savedItem);
               String name = savedItem.getName();
//                item.setName(savedItem.getName());
//                item.setBrand(savedItem.getBrand());
//                item.setYear(savedItem.getBrand());
//                cartsItem.add(item);
            }

            cart.setItems(cartsItem);

        }
        return carts;
    }

    /**
     * The method show cart with all information about it.
     *
     * @param cartId This is cart's id.
     * @return found cart.
     */
    @Override
    public Cart showCart(UUID cartId) {
        return cartRepository.findCartByCartId(cartId);
    }

    /**
     * The method delete items from cart.
     *
     * @param cartId   This is cart's id which from needed to delete item.
     * @param cart This parameter consist items which needed delete.
     * @return cart without items which needed to be removed.
     */
    @Override
    public Cart deleteFromCart(UUID cartId, Cart cart) {
        Cart savedCart = cartRepository.findCartByCartId(cartId);
        savedCart.getItems().remove(cart.getItems());
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
        cart.setItems(cart.getItems());
        return cartRepository.insert(cart);
    }

    /**
     * This method update cart.
     *
     * @param cartId   This is cart's id which needed to update.
     * @param cart This is updated cart.
     * @return Updated cart.
     */
    @Override
    public Cart updateCart(UUID cartId, Cart cart) {
        Cart savedCart = cartRepository.findCartByCartId(cartId);

        savedCart.setItems(cart.getItems());
        //     savedCart.setNumOfItems(cart.getNumOfItems());

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
    }
}
