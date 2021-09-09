package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.exception.NotFoundEntityException;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The controller of Item.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    /**
     * The method show item.
     *
     * @param itemId This is item's id which should be viewed.
     * @return response with body of item and status ok.
     */
    @GetMapping("{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable(value = "itemId") UUID itemId) {
        Item item;
        try {
            item = itemService.getById(itemId);
        } catch (IllegalStateException e) {
            log.error("IN ItemController getItemById - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundEntityException e) {
            log.error("In ItemController getItemById - item by itemId: {} is null", itemId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * The method show items.
     *
     * @return response with body of items and status ok.
     */
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    /**
     * The method add new item.
     *
     * @param item This is item with its information and body.
     * @return response with body of created item and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.saveItem(item), HttpStatus.CREATED);
    }

    /**
     * The method update item.
     *
     * @param id   This is item's id which should be updated.
     * @param item This is new body for item which should be updated.
     * @return response with body of updated item and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable(value = "id") UUID id, @RequestBody Item item) {
        Item updatedItem;
        try {
            updatedItem = itemService.updateItem(id, item);
        } catch (IllegalStateException e) {
            log.error("IN ItemController updateItem - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundEntityException e) {
            log.error("In ItemController updateItem - item by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    /**
     * The method delete item.
     *
     * @param id This is item's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") UUID id) {
        try {
            itemService.deleteItemById(id);
        } catch (IllegalStateException e) {
            log.error("In ItemController deleteItem - item by id {} is not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
