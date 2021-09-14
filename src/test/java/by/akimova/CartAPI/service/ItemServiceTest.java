package by.akimova.CartAPI.service;

import by.akimova.CartAPI.controller.ItemController;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.ItemService;
import by.akimova.CartAPI.service.impl.ItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceTest {
    @Autowired
    private ItemServiceImpl itemServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemController itemController;


    @Test
    void getAllItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andDo(print())
                .andExpect(status().isOk());
          //      .andExpect(content().string());
    }

    @Test
    void getById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/items/6d1ad16e-104e-4c20-9787-689c15d945a0")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

/*    @Test
    @WithMockUser(username = "maryCo@gmail.com", password = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
     Item item = new Item();
     item.setItemId(UUID.randomUUID());
     item.setName("phone");
     item.setModel("A120");
     item.setBrand("lg");
     item.setYear("2020");

        itemServiceImpl.saveItem(item);
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }*/
}