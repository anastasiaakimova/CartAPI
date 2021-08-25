package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The controller of Item.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * The method show items.
     *
     * @return response with body of items and status ok.
     */
    @GetMapping
    public ResponseEntity getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    /**
     * The method show item.
     *
     * @param id This is item's id which should be viewed.
     * @return response with body of item and status ok.
     */
    @GetMapping("/{id}")
    public ResponseEntity showItem(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(itemService.showItem(id));
    }

    /**
     * The method add new item.
     *
     * @param item This is item with its information and body.
     * @return response with body of created item and status ok.
     */
    @PostMapping
    public ResponseEntity addItem(@RequestBody Item item) {
        itemService.saveItem(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * The method update item.
     *
     * @param id   This is item's id which should be updated.
     * @param item This is new body for item which should be updated.
     * @return response with body of updated item and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity updateItem(@PathVariable(value = "id") UUID id, @RequestBody Item item) {
        itemService.updateItem(id, item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * The method delete item.
     *
     * @param id This is item's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable(value = "id") UUID id) {
        itemService.deleteItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
