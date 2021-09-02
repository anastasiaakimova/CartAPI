package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for class {@link Cart}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository("cartRepository")
public interface CartRepository extends MongoRepository<Cart, String> {
    void deleteByCartId(UUID cartId);

    Cart findCartByCartId(UUID cartId);

}
