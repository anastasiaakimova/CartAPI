package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotAccessException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The controller of Cart.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * The method get list of carts.
     *
     * @return response with body of carts and status ok.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAll());
    }

    /**
     * This method get cart by id.
     *
     * @param id This is cart's id.
     * @return response with body of cart and status ok.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<?> getCartById(@PathVariable(value = "id") UUID id) {
        Cart cart;
        try {
            cart = cartService.getCartById(id);
        } catch (NotValidUsernameException e) {
            log.error("IN CartController getCartById - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN CartController getCartById - cart by id {} not found ", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cart);
    }

    /**
     * This method get cart by userId.
     *
     * @param id This is user's id.
     * @return response with body of cart and status ok.
     */
    @GetMapping("/userId/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<?> getCartByUserId(@PathVariable(value = "id") UUID id) {
        Cart cart;
        try {
            cart = cartService.getCartByUserId(id);
        } catch (NotValidUsernameException e) {
            log.error("IN CartController getCartByUserId - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN CartController getCartByUserId - cart by userId {} not found ", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cart);
    }

    /**
     * The method add new cart.
     *
     * @param cart This is cart with its information and body.
     * @return response with body of created cart and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody Cart cart) {
        return new ResponseEntity<>(cartService.saveCart(cart), HttpStatus.CREATED);
    }

    /**
     * The method update cart.
     *
     * @param cartId This is cart's id which should be updated.
     * @param cart   This is new body for cart which should be updated.
     * @return response with body of updated cart and status ok.
     */
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable(value = "cartId") UUID cartId, @RequestBody Cart cart) {
        Cart updatedCart;
        try {
            updatedCart = cartService.updateCart(cartId, cart);
        } catch (NotValidUsernameException e) {
            log.error("IN CartController updateCart - cartId is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN CartController updateCart - cart by id {} not found", cartId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotAccessException e) {
            log.error("IN CartController updateCart - user can't do this");
            return new ResponseEntity<>("This user can't update this cart", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    /**
     * The method delete cart.
     *
     * @param cartId This is cart's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable(value = "cartId") UUID cartId) {
        try {
            cartService.deleteCartById(cartId);
        } catch (EntityNotFoundException e) {
            log.error("IN CartController deleteCart - cart by id {} not found", cartId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NotAccessException e) {
            log.error("IN CartController deleteCart - user can't do this");
            return new ResponseEntity<>("This user can't delete this cart",HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * The method delete items from cart.
     *
     * @param cartId  This is cart's id from items should be deleted.
     * @param itemIds List of id of items which should be deleted.
     * @return response status ok and message "updated".
     */

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteFromCart(@PathVariable(value = "cartId") UUID
                                                    cartId, @RequestBody List<UUID> itemIds) {
        Cart cart;
        try {
            cart = cartService.deleteFromCart(cartId, itemIds);
        } catch (NotValidUsernameException e) {
            log.error("IN CartController deleteFromCart - cartId is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN CartController deleteFromCart - cart with id {} is not found", cartId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NotAccessException e){
            log.error("IN CartController deleteFromCart - user can't do this");
            return new ResponseEntity<>("This user can't delete items from this cart", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
