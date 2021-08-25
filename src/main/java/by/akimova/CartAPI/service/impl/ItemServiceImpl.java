package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The class is implementation of item's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * The method show item with all information about it.
     *
     * @param id This is item's id.
     * @return found item.
     */
    @Override
    public Item showItem(UUID id) {
        return itemRepository.findById(id);
    }

    /**
     * The method add new cart.
     *
     * @param item This is item with information about it, and it's fields
     * @return Saved itemm.
     */
    @Override
    public Item saveItem(Item item) {
        item.setId(UUID.randomUUID());
        return itemRepository.insert(item);
    }

    /**
     * This method update item.
     *
     * @param id   This is item's id which needed to update.
     * @param item This is updated item.
     * @return Updated item.
     */
    @Override
    public Item updateItem(UUID id, Item item) {
        Item savedItem = itemRepository.findById(id);

        savedItem.setName(item.getName());
        savedItem.setBrand(item.getBrand());
        savedItem.setNumber(item.getNumber());
        savedItem.setYear(item.getYear());

        return itemRepository.save(savedItem);
    }

    /**
     * This method delete item.
     *
     * @param id This is item's id which needed to delete.
     */
    @Override
    public void deleteItemById(UUID id) {
        itemRepository.deleteById(id);
    }
}
