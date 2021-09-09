package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.ValidationException;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The class is implementation of  {@link ItemService} interface.
 * Wrapper for {@link ItemRepository} + business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
@Slf4j
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    /**
     * The method show all items with all information about it.
     *
     * @return list of items.
     */
    @Override
    public List<Item> getAllItems() {
        List<Item> result = itemRepository.findAll();
        log.info("IN getAll - {} items found", result.size());
        return result;
    }

    /**
     * The method show item with all information about it.
     *
     * @param itemId This is item's id.
     * @return found item.
     */
    @Override
    public Item getById(UUID itemId) throws EntityNotFoundException, ValidationException {
        if (itemId == null) {
            log.error("IN getById - id is null ");
            throw new ValidationException("itemId is null");
        }
        Item item = itemRepository.findItemByItemId(itemId);
        if (item == null){
            log.error("IN getById - no item found by id: {}", itemId);
            throw new EntityNotFoundException("Item not found");
        }
        log.info("IN getById - item: {} found by id: {}", item, itemId);
        return item;
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
        log.info("IN saveItem - new item with id: {} successfully added", item.getItemId());
        return itemRepository.insert(item);
    }

    /**
     * This method update item.
     *
     * @param itemId This is item's id which needed to update.
     * @param item   This is updated item.
     * @return Updated item.
     */
    @Override
    public Item updateItem(UUID itemId, Item item) throws EntityNotFoundException, ValidationException {
        if (item == null) {
            log.error("IN updateItem - item is null");
            throw new ValidationException("item is null");
        }
        Item dbItem = itemRepository.findItemByItemId(itemId);
        if (dbItem == null){
            log.error("IN updateItem - no item found by id: {}", itemId);
            throw new EntityNotFoundException( "item is null");
        }
        dbItem.setName(item.getName());
        dbItem.setBrand(item.getBrand());
        dbItem.setModel(item.getModel());
        dbItem.setYear(item.getYear());

        log.info("IN updateItem - item with id: {} successfully edited ", itemId);
        return itemRepository.save(dbItem);
    }

    /**
     * This method delete item.
     *
     * @param itemId This is item's id which needed to delete.
     */
    @Override
    public void deleteItemById(UUID itemId) {
        itemRepository.deleteItemByItemId(itemId);
        log.info("IN delete - item with id: {} successfully deleted", itemId);
    }
}
