package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Repository for Cart.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface CartRepository extends MongoRepository<Cart, String> {
    void deleteById(UUID id);

    Cart findById(UUID id);

}
