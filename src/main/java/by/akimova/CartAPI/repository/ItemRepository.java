package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for class {@link Item}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository("itemRepository")
public interface ItemRepository extends MongoRepository<Item, String> {
    void deleteItemByItemId(UUID itemId);

    Item findItemByItemId(UUID itemId);

}
