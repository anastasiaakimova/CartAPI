package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotAccessException;
import by.akimova.CartAPI.model.Cart;
import by.akimova.CartAPI.model.Item;
import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.CartRepository;
import by.akimova.CartAPI.security.jwt.SecurityUser;
import by.akimova.CartAPI.service.CartService;
import by.akimova.CartAPI.service.impl.CartServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Testing class for {@link CartService}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private SecurityContextHolder contextHolder;

    @Mock
    private SecurityContext context;

    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    private Cart cart1;
    private Cart cart2;
    private Cart cartToSave;
    private User user1;
    private User user2;
    private List<Cart> carts;
    private List<Item> items;

    @Mock
    private Authentication auth;
    private SecurityUser principal;

    @BeforeEach
    public void setUp() {
        carts = new ArrayList<>();
        items = new ArrayList<>();

        user1 = new User();
        user1.setUserId(UUID.randomUUID());
        user1.setMail("user@mail.com");
        user1.setName("qwerty");
        user1.setRole(Role.USER);
        user1.setPassword("user");

        cart1 = new Cart();
        cart1.setCartId(UUID.randomUUID());
        cart1.setUserId(user1.getUserId());
        cart1.setItems(items);
        carts.add(cart1);

        user2 = new User();
        user2.setUserId(UUID.randomUUID());
        user2.setMail("user@mail.com");
        user2.setName("qwerty");
        user2.setRole(Role.USER);
        user2.setPassword("user");

        cart2 = new Cart();
        cart2.setCartId(UUID.randomUUID());
        cart2.setUserId(user2.getUserId());
        cart2.setItems(items);
        carts.add(cart2);

        cartToSave = new Cart();
        cartToSave.setUserId(user1.getUserId());
        cartToSave.setItems(items);

        User user = new User();
  /*      user.setName("asdfg");
        user.setMail("asdf@gmail.com");
        user.setPassword("admin");*/

     //   principal = SecurityUser.fromUser(user);
    }

    @AfterEach
    public void tearDown() {
        cart1 = cart2 = null;
        carts = null;
    }

    @Test
    void getAll() {
        when(cartRepository.findAll()).thenReturn(carts);
        List<Cart> carts1 = cartServiceImpl.getAll();
        assertEquals(carts1, carts);
        verify(cartRepository, times(1)).findAll();
    }

    @Test
    void getCartById() throws Exception {
        when(cartRepository.findCartByCartId(cart1.getCartId())).thenReturn(cart1);
        assertThat(cartServiceImpl.getCartById((cart1.getCartId()))).isEqualTo(cart1);
    }

    @Test
    void getCartByUserId() throws Exception {
        when(cartRepository.findCartByUserId(cart1.getUserId())).thenReturn(cart1);
        assertThat(cartServiceImpl.getCartByUserId((cart1.getUserId()))).isEqualTo(cart1);
    }

    @Test
    void saveCart() {
        when(cartRepository.insert(any(Cart.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Cart savedCart = cartServiceImpl.saveCart(cartToSave);
        assertThat(savedCart.getCartId()).isNotNull();
        assertThat(savedCart.getUserId()).isSameAs(cartToSave.getUserId());
        assertThat(savedCart.getItems()).isSameAs(cartToSave.getItems());
    }

    @Test
    void deleteCartById() throws NotAccessException, EntityNotFoundException {
        when(contextHolder.getContext()).thenReturn(context);
        when(context.getAuthentication()).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(principal);
        cartServiceImpl.deleteCartById(cart1.getCartId());
    }
}
