package by.akimova.CartAPI.service;

import by.akimova.CartAPI.model.Item;

import java.util.UUID;

public interface ItemService {

    Item showItem(UUID id);

    Item saveItem(Item item);

    Item updateItem(UUID id, Item item);

    void deleteItemById(UUID id);
}
