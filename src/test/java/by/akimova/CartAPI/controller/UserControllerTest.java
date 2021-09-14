package by.akimova.CartAPI.controller;

import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
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

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getAllUsers() throws Exception {
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
    void getUserById() throws Exception {
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
    void addUser() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        when(userService.saveUser(ArgumentMatchers.any(User.class))).thenReturn(user);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "user:write", username = "test")
    void getUserByMail() throws Exception {
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
    void updateUser() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

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
    void deleteUser() throws Exception {

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