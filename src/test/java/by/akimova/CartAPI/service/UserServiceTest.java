package by.akimova.CartAPI.service;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.service.UserService;
import by.akimova.CartAPI.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Autowired
    private ObjectMapper objectMapper;
    /*    @Autowired
        private UserService userService;*/
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    UserService userService;
    private User user1;
    private User user2;
    List<User> users;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();

        user1 = new User();
        user1.setUserId(UUID.randomUUID());
        user1.setName("Alex");
        user1.setMail("asd@mail");
        user1.setRole(Role.USER);
        user1.setPassword("user");

        users.add(user1);

        user2 = new User();
        user2.setUserId(UUID.randomUUID());
        user2.setName("Qwerty");
        user2.setMail("zxcvb@mail");
        user2.setRole(Role.ADMIN);
        user2.setPassword("admin");

        users.add(user2);
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = null;
        users = null;
    }

    @Test
    void getAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(users);
        List<User> users1 = userServiceImpl.getAllUsers();
        assertEquals(users1, users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getById() throws Exception {
        when(userRepository.findUserByUserId(user1.getUserId())).thenReturn(user1);
        assertThat(userServiceImpl.getById((user1.getUserId()))).isEqualTo(user1);
    }

    @Test
    void saveUser() throws Exception {
       when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user1);
        userServiceImpl.saveUser(user1);
        // verify(userRepository,times(1)).save(any(User.class));
        // assertThat(user1.getUserId()).isSameAs(user1.getUserId());
        verify(userRepository, times(1)).save(user1);


        //   assertThat(user).isNotNull();
        //  verify(userRepository).save(any(User.class));
/*
        given(userRepository.save(user1)).willReturn(user1);

        User savedUser = userServiceImpl.saveUser(user1);

        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));*/
    }

    @Test
    void findByMail() throws Exception {
        when(userRepository.findByMail(user1.getMail())).thenReturn(java.util.Optional.of(user1));
        assertThat(userServiceImpl.findByMail((user1.getMail()))).isEqualTo(Optional.of(user1));
    }

    @Test
    void updateUser() throws Exception {
        when(userRepository.save(user1)).thenReturn(user1);
        userServiceImpl.saveUser(user1);

        user1 = user2;

        when(userRepository.save(user1)).thenReturn(user1);
        assertThat(userServiceImpl.updateUser(user1.getUserId(), user2)).isEqualTo(user1);
  /*      User updatedUser = userServiceImpl.updateUser(user1.getUserId(), user2);
        assertThat(updatedUser).isNotNull();
        verify(userRepository).save(any(User.class));*/
    }

    @Test
    void deleteUserById() throws Exception {
        userServiceImpl.deleteUserById(user1.getUserId());
        verify(userRepository, times(1)).deleteUserByUserId(user1.getUserId());
    }
}