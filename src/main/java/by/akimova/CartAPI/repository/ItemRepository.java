package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ItemRepository extends MongoRepository<Item, String> {
    void deleteById(UUID id);

    Item findById(UUID id);

}
