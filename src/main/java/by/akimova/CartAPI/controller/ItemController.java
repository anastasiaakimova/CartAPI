package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        } catch (NotValidUsernameException e) {
            log.error("IN ItemController getItemById - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
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
    @PreAuthorize("hasAuthority('user:write')")
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
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> updateItem(@PathVariable(value = "id") UUID id, @RequestBody Item item) {
        Item updatedItem;
        try {
            updatedItem = itemService.updateItem(id, item);
        } catch (NotValidUsernameException e) {
            log.error("IN ItemController updateItem - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
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
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> deleteItem(@PathVariable(value = "id") UUID id) {
        itemService.deleteItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
