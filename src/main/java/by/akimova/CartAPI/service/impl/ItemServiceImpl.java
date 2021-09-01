package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The class is implementation of item's business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * The method show all items with all information about it.
     *
     * @return all items.
     */
    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * The method show item with all information about it.
     *
     * @param itemId This is item's id.
     * @return found item.
     */
    @Override
    public Item getById(UUID itemId) {
        Item result = itemRepository.findItemByItemId(itemId);

        if (result == null) {
            log.warn("IN findById - no item found by id: {}", itemId);
            return null;
        }

        log.info("IN findById - item: {} found by id: {}", result, itemId);
        return result;
    }

    /**
     * The method add new cart.
     *
     * @param item This is item with information about it, and it's fields
     * @return Saved item.
     */
    @Override
    public Item saveItem(Item item) {
        item.setItemId(UUID.randomUUID());
        return itemRepository.insert(item);
    }

    /**
     * This method update item.
     *
     * @param itemId   This is item's id which needed to update.
     * @param item This is updated item.
     * @return Updated item.
     */
    @Override
    public Item updateItem(UUID itemId, Item item) {
        Item savedItem = itemRepository.findItemByItemId(itemId);

        savedItem.setName(item.getName());
        savedItem.setBrand(item.getBrand());
        savedItem.setYear(item.getYear());

        return itemRepository.save(savedItem);
    }

    /**
     * This method delete item.
     *
     * @param itemId This is item's id which needed to delete.
     */
    @Override
    public void deleteItemById(UUID itemId) {
        itemRepository.deleteItemByItemId(itemId);
    }
}
