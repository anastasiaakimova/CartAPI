package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
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
    private Item item1;
    private Item item2;
    private List<Item> items;
    private List<UUID> itemIds;

    @BeforeEach
    public void setUp() {
        item1 = new Item();
        item1.setItemId(UUID.randomUUID());

        item2 = new Item();
        item2.setItemId(UUID.randomUUID());

        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        cart1 = new Cart();
        cart1.setCartId(UUID.randomUUID());
        cart1.setUserId(UUID.randomUUID());
        cart1.setItems(items);

        cart2 = new Cart();
        cart2.setCartId(UUID.randomUUID());
        cart2.setUserId(UUID.randomUUID());
        cart2.setItems(items);

        carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        itemIds = new ArrayList<>();
        itemIds.add(item1.getItemId());

    }

    @AfterEach
    public void tearDown() {
        item1 = item2 = null;
        cart1 = cart2 = null;
        carts = null;
        items = null;
        itemIds = null;
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getAllCarts_success() throws Exception {
        when(cartService.getAll()).thenReturn(carts);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartById_success() throws Exception {
        when(cartService.getCartById(cart1.getCartId())).thenReturn(cart1);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/" + cart1.getCartId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartById_badRequest() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(cartService.getCartById(uuid)).thenThrow(new NotValidUsernameException("cartId is null"));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartById_notFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(cartService.getCartById(uuid)).thenThrow(new EntityNotFoundException("Cart not found"));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartByUserId_success() throws Exception {

        when(cartService.getCartByUserId(cart1.getUserId())).thenReturn(cart1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/userId/" + cart1.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getCartByUserId_notFound() throws Exception {

        UUID uuid = UUID.randomUUID();
        when(cartService.getCartByUserId(uuid)).thenThrow(new EntityNotFoundException("Cart doesn't exist "));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/userId/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void addCart_success() throws Exception {
        when(cartService.saveCart(ArgumentMatchers.any(Cart.class))).thenReturn(cart1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cart1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void updateCart_success() throws Exception {
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
    void updateCart_badRequest() throws Exception {
        Cart cart = new Cart();
        mockMvc
                .perform(MockMvcRequestBuilders.put("/carts/" + cart.getCartId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteFromCart_success() throws Exception {
        UUID cartId = cart1.getCartId();

        when(cartService.deleteFromCart(cart1.getCartId(), itemIds)).thenReturn(cart1);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/carts/" + cartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                          .content(this.objectMapper.writeValueAsString(itemIds)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteFromCart_badRequest() throws Exception {
        UUID cartId = cart1.getCartId();

        when(cartService.deleteFromCart(cartId, itemIds)).thenThrow(new NotValidUsernameException("cartId is null "));

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/carts/" + cartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cart1)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteCart_success() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/carts/" + cart1.getCartId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteCart_notFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        doThrow(new EntityNotFoundException("cart not found")).when(cartService).deleteCartById(isA(UUID.class));
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/carts/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}