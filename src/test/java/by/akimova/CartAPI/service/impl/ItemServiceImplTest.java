package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.repository.ItemRepository;
import by.akimova.CartAPI.service.ItemService;
import by.akimova.CartAPI.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Testing class for {@link ItemService}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemServiceImpl;

    private Item item1;
    private Item item2;
    private Item itemToSave;
    private Item nullItem;
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

        itemToSave = new Item();
        itemToSave.setName("tv");
        itemToSave.setModel("120");
        itemToSave.setYear("2020");
        itemToSave.setBrand("lg");

        nullItem = new Item();
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
    void getById_success() throws Exception {
        when(itemRepository.findItemByItemId(item1.getItemId())).thenReturn(item1);
        Item item = itemServiceImpl.getById((item1.getItemId()));
        assertThat(item.getItemId()).isEqualTo(item1.getItemId());
        verify(itemRepository, times(1)).findItemByItemId(item1.getItemId());
    }

    @Test
    void getById_NotValidUsernameException() throws Exception {
        Item item = new Item();
        when(itemRepository.findItemByItemId(item.getItemId())).thenReturn(item);
        assertThatThrownBy(() -> itemServiceImpl.getById(null))
                .isInstanceOf(NotValidUsernameException.class).hasMessage("itemId is null");
    }

    @Test
    void getById_EntityNotFoundException() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(itemRepository.findItemByItemId(uuid)).thenReturn(null);
        assertThatThrownBy(() -> itemServiceImpl.getById(uuid))
                .isInstanceOf(EntityNotFoundException.class).hasMessage("Item not found");
    }

    @Test
    void saveItem() throws Exception {
        when(itemRepository.insert(any(Item.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Item savedItem = itemServiceImpl.saveItem(itemToSave);
        assertThat(savedItem.getItemId()).isNotNull();
        assertThat(savedItem.getItemId()).isSameAs(itemToSave.getItemId());
        verify(itemRepository, times(1)).insert(itemToSave);
    }

    @Test
    void updateItem_success() throws Exception{
        when(itemRepository.findItemByItemId(item1.getItemId())).thenReturn(item1);
        Item item = itemServiceImpl.getById((item1.getItemId()));
        when(itemRepository.save(any(Item.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Item updatedItem = itemServiceImpl.updateItem(item.getItemId(), itemToSave);
        assertThat(updatedItem.getName()).isEqualTo(itemToSave.getName());
    }

    @Test
    void updateItem_NotValidUsernameException() throws Exception{
        when(itemRepository.save(any(Item.class))).thenReturn(null);
        assertThatThrownBy(() ->itemServiceImpl.updateItem(item1.getItemId(), null))
                .isInstanceOf(NotValidUsernameException.class).hasMessage("item is null");
    }

    @Test
    void updateItem_EntityNotFoundException() throws Exception{
        UUID uuid = UUID.randomUUID();
        when(itemRepository.save(any(Item.class))).thenReturn(null);
        assertThatThrownBy(() ->itemServiceImpl.updateItem(uuid, item1))
                .isInstanceOf(EntityNotFoundException.class).hasMessage("item is null");
    }

    @Test
    void deleteItemById() throws Exception {
        itemServiceImpl.deleteItemById(item1.getItemId());
        verify(itemRepository, times(1)).deleteItemByItemId(item1.getItemId());
    }


}