package by.akimova.CartAPI;

import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class CartApiApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads() {
    }

 //   @Test
    public void postUser() throws Exception {
        User user = new User();

        user.setUserId(UUID.randomUUID());
        user.setRole("USER");
        user.setPassword("user");
        user.setName("Misha");
        user.setMail("mishaGHJ@mail.com");
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNotEmpty())
                .andExpect(jsonPath("$._id").isNotEmpty())
                .andExpect(jsonPath("$.role").isNotEmpty())
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Misha"))
                .andExpect(jsonPath("$.mail").value("mishaGHJ@mail.com"));
    }

  //  @Test
    public void findById() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setRole("USER");
        user.setPassword("user");
        user.setName("Misha");
        user.setMail("mishaGHJ@mail.com");

        when(userRepository.findUserByUserId(user.getUserId())).thenReturn(user);

        mockMvc.perform(get("/users/{id}", user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").isNotEmpty())
                .andExpect(jsonPath("$._id").isNotEmpty())
                .andExpect(jsonPath("$.role", is(user.getRole())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is("Misha")))
                .andExpect(jsonPath("$.mail", is("mishaGHJ@mail.com")));
    }
}
