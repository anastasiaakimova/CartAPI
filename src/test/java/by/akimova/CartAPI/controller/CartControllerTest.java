package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing class for {@link CartController}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    @MockBean
    private CartService cartService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Cart cart1;
    private Cart cart2;
    private List<Cart> carts;
    private List<Item> items;
    @BeforeEach
    public void setUp() {
        carts = new ArrayList<>();
        items = new ArrayList<>();

        cart1 = new Cart();
        cart1.setCartId(UUID.randomUUID());
        cart1.setUserId(UUID.randomUUID());
        cart1.setItems(items);

        carts.add(cart1);

        cart2 = new Cart();
        cart2.setCartId(UUID.randomUUID());
        cart2.setUserId(UUID.randomUUID());
        cart2.setItems(items);

        carts.add(cart2);
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getAllCarts() throws Exception{
        when(cartService.getAll()).thenReturn(carts);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartById() throws Exception {
        when(cartService.getCartById(cart1.getCartId())).thenReturn(cart1);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/" + cart1.getCartId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartByUserId() {
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void addCart() {
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void updateCart() throws Exception{
        given(cartService.updateCart(cart1.getCartId(), cart2)).willReturn(cart1);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/carts/" + cart1.getCartId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cart1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteCart() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/carts/" + cart1.getCartId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteFromCart() {
    }
}