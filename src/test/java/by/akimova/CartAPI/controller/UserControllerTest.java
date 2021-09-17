package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.exception.EntityNotFoundException;
import by.akimova.CartAPI.exception.NotFreeUsernameException;
import by.akimova.CartAPI.exception.NotValidUsernameException;
import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.service.UserService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.data.mongodb.util.BsonUtils.toJson;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing class for {@link UserController}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    private User user1;
    private User user2;
    private User userToSave;
    private List<User> users;


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

        userToSave = new User();
        userToSave.setMail("qwerty@mail.com");
        userToSave.setPassword(bCryptPasswordEncoder.encode("user"));

    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getAllUsers_success() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        List<User> users = singletonList(user);

        when(userService.getAllUsers()).thenReturn(users);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getUserById_badRequest() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(userService.getById(uuid)).thenThrow(new NotValidUsernameException("userId is null"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getUserById_notFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userService.getById(uuid)).thenThrow(new EntityNotFoundException("user not found"));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getUserById_success() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        when(userService.getById(user.getUserId())).thenReturn(user);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/" + user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void addUser_success() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("qwert@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        when(userService.saveUser(ArgumentMatchers.any(User.class))).thenReturn(user);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void addUser_badRequest() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asd@mail");
        user.setRole(Role.USER);
        user.setPassword("user");

        when(userService.saveUser(ArgumentMatchers.any(User.class))).thenThrow(new NotFreeUsernameException("This username is already taken"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                        .content(this.objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getUserByMail_success() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        when(userService.findByMail(user.getMail())).thenReturn(java.util.Optional.of(user));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/mail/" + user.getMail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getUserByMail_notFound() throws Exception {

        String mail = "asdf@mail.com";

        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/mail/" + mail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void updateUser_success() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword("admin");

        given(userService.updateUser(user.getUserId(), user)).willReturn(user);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/users/" + user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void updateUser_badRequest() throws Exception {
        User user = new User();

        when(userService.updateUser(user1.getUserId(), user)).thenThrow(new NotValidUsernameException("user is null"));

        mockMvc
                .perform(MockMvcRequestBuilders.put("/users/" + user1.getUserId())
                        .content(this.objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void updateUser_notFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        User user = new User();

        when(userService.updateUser(null, user)).thenThrow(new EntityNotFoundException("user not found"));

        mockMvc
                .perform(MockMvcRequestBuilders.put("/users/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void deleteUser_success() throws Exception {

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/users/" + user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}