package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The controller of Cart.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * The method show cart with items.
     *
     * @param id This is cart's id which should be viewed.
     * @return response with body of cart and status ok.
     */
    @GetMapping("/{id}")
    public ResponseEntity showCart(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(cartService.showCart(id));
    }

    /**
     * The method add new cart.
     *
     * @param cart This is cart with its information and body.
     * @return response with body of created cart and status ok.
     */
    @PostMapping
    public ResponseEntity addCart(@RequestBody Cart cart) {
        cartService.saveCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    /**
     * The method update cart.
     *
     * @param id   This is cart's id which should be updated.
     * @param cart This is new body for cart which should be updated.
     * @return response with body of updated cart and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity updateCart(@PathVariable(value = "id") UUID id, @RequestBody Cart cart) {
        cartService.updateCart(id, cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * The method delete cart.
     *
     * @param id This is cart's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCart(@PathVariable(value = "id") UUID id) {
        cartService.deleteCartById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * The method delete items from cart.
     *
     * @param id   This is cart's id from items should be deleted.
     * @param cart this is body with.
     * @return response status ok and message "updated".
     */
    @PatchMapping("/{id}")
    public ResponseEntity deleteFromCart(@PathVariable(value = "id") UUID id, Cart cart) {
        cartService.deleteFromCart(id, cart);
        return ResponseEntity.ok("updated");
    }
}
