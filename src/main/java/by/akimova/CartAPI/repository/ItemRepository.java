package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository("itemRepository")
public interface ItemRepository extends MongoRepository<Item, String> {
    void deleteItemByItemId(UUID itemId);

    Item findItemByItemId(UUID itemId);

}
