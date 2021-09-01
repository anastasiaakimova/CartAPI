package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * The controller of Cart.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * The method show carts.
     *
     * @return response with body of carts and status ok.
     */
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAll());
    }

    /**
     * The method add new cart.
     *
     * @param cart This is cart with its information and body.
     * @return response with body of created cart and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody Cart cart) {
        cartService.saveCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    /**
     * The method update cart.
     *
     * @param cartId   This is cart's id which should be updated.
     * @param cart This is new body for cart which should be updated.
     * @return response with body of updated cart and status ok.
     */
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable(value = "cartId") UUID cartId, @RequestBody Cart cart) {
        cartService.updateCart(cartId, cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * The method delete cart.
     *
     * @param cartId This is cart's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable(value = "cartId") UUID cartId) {
        cartService.deleteCartById(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * The method delete items from cart.
     *
     * @param cartId   This is cart's id from items should be deleted.
     * @param cart this is body with.
     * @return response status ok and message "updated".
     */
    @PatchMapping("/{cartId}")
    public ResponseEntity<?> deleteFromCart(@PathVariable(value = "cartId") UUID cartId, Cart cart) {
        cartService.deleteFromCart(cartId, cart);
        return ResponseEntity.ok("updated");
    }
}
