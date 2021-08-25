package by.akimova.CartAPI.service;

import by.akimova.CartAPI.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    List<Item> getAllItems();

    Item showItem(UUID id);

    Item saveItem(Item item);

    Item updateItem(UUID id, Item item);

    void deleteItemById(UUID id);
}
