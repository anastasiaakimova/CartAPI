package by.akimova.CartAPI.service;

import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Testing class for {@link ItemService}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemServiceImpl;

    private Item item1;
    private Item item2;
    private List<Item> items;

    @BeforeEach
    public void setUp() {
        items = new ArrayList<>();

        item1 = new Item();
        item1.setItemId(UUID.randomUUID());
        item1.setName("phone");
        item1.setModel("A120");
        item1.setBrand("lg");
        item1.setYear("2020");
        items.add(item1);

        item2 = new Item();
        item2.setItemId(UUID.randomUUID());
        item2.setName("tv");
        item2.setModel("asdf4");
        item2.setBrand("qwerty");
        item2.setYear("2013");
        items.add(item2);
    }

    @AfterEach
    public void tearDown() {
        item1 = item2 = null;
        items = null;
    }

    @Test
    void getAllItems() throws Exception {
        when(itemRepository.findAll()).thenReturn(items);
        List<Item> items1 = itemServiceImpl.getAllItems();
        assertEquals(items1, items);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void getById() throws Exception {
        when(itemRepository.findItemByItemId(item1.getItemId())).thenReturn(item1);
        assertThat(itemServiceImpl.getById((item1.getItemId()))).isEqualTo(item1);
    }

    @Test
    void saveItem() throws Exception {
        when(itemRepository.save(any(Item.class))).thenReturn(item1);
        Item savedItem = itemRepository.save(item1);
        assertThat(savedItem.getItemId()).isNotNull();
        assertThat(savedItem.getItemId()).isSameAs(item1.getItemId());
        verify(itemRepository, times(1)).save(item1);
    }

    @Test
    void deleteItemById() throws Exception {
        itemServiceImpl.deleteItemById(item1.getItemId());
        verify(itemRepository, times(1)).deleteItemByItemId(item1.getItemId());
    }
}