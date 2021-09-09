package by.akimova.CartAPI.service;

import by.akimova.CartAPI.exception.NotFoundEntityException;
import by.akimova.CartAPI.model.Item;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for class {@link Item}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface ItemService {
    List<Item> getAllItems();

    Item getById(UUID id) throws NotFoundEntityException;

    Item saveItem(Item item);

    Item updateItem(UUID id, Item item) throws NotFoundEntityException;

    void deleteItemById(UUID id);
}
