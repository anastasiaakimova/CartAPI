package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing class for {@link ItemController}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
    @MockBean
    private ItemService itemService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Item item1;
    private Item item2;
    private List<Item> items;

    @BeforeEach
    public void setUp() {
        items = new ArrayList<>();

        item1 = new Item();
        item1.setItemId(UUID.randomUUID());
        item1.setName("phone");
        item1.setBrand("lg");
        item1.setModel("Asd23");
        item1.setYear("2020");

        items.add(item1);

        item2 = new Item();
        item2.setItemId(UUID.randomUUID());
        item2.setName("tv");
        item2.setBrand("samsung");
        item2.setModel("qwert5");
        item2.setYear("2019");

        items.add(item2);
    }

    @Test
    void getItemById() throws Exception {
        when(itemService.getById(item1.getItemId())).thenReturn(item1);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/items/" + item1.getItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllItems() throws Exception {
        when(itemService.getAllItems()).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void addItem() throws Exception {
        when(itemService.saveItem(ArgumentMatchers.any(Item.class))).thenReturn(item1);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(item1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void updateItem() throws Exception {
        given(itemService.updateItem(item1.getItemId(), item2)).willReturn(item1);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/items/" + item1.getItemId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(item1)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteItem() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/items/" + item1.getItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}