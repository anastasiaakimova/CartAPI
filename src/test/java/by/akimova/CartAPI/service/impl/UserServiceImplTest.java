package by.akimova.CartAPI.service.impl;

import by.akimova.CartAPI.config.SecurityConfig;
import by.akimova.CartAPI.controller.UserController;
import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
/*@WithMockUser(username = "maryCo@gmail.com",password = "admin", roles = "ADMIN")*/
class UserServiceImplTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @Mock
    private WebApplicationContext webApplicationContext;
/*

    @BeforeEach
    public void setup(){
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                */
/* .defaultRequest(get("/users")
                         .with(user("maryCo@gmail.com").password(bCryptPasswordEncoder.encode("admin")).roles("ADMIN")))
                 .apply(springSecurity())*//*

                .build();
    }
*/

    @Test
    @WithMockUser("ADMIN")
    void getAllUsers() throws Exception {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("asdfg");
        user.setMail("asdf@mail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        List<User> users = singletonList(user);

        when(userServiceImpl.getAllUsers()).thenReturn(users);

        this.
       mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                          //.with(user("maryCo@gmail.com").password(bCryptPasswordEncoder.encode("admin")).roles("ADMIN")))
                       // .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
             //   .andExpect((ResultMatcher) jsonPath("$[0].user", is(user)));
    }
    /*    @Test
        public void saveUser() throws NotFreeUsernameException, Exception {
            User user = new User();

            user.setUserId(UUID.randomUUID());
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode("user"));
            user.setName("Misha");
            user.setMail("mishaGHJ@mail.com");

            mockMvc.perform(
                            post("/users")
                                    .content(objectMapper.writeValueAsString(user))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated());
        }*/
/*
    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
*/



/*
    @Test
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(formLogin("/auth/login").user("maryCo@gmail.com").password(bCryptPasswordEncoder.encode("admin")))
                .andDo(print())
                .andExpect(status().isOk());
    }
*/

/*    @Test
    public void nonexistentUserCannotGetToken() throws Exception {
        String username = "maryCo@gmail.com";
        String password = "admin";

        String body = "{\"username\":\"" + username
                + "\", \"password\":\"" + password + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .content(body))
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    public void existentUserCanGetTokenAndAuthentication() throws Exception {
        String username = "maryCo@gmail.com";
        String password = "admin";

        String body = "{\"username\":\"" + username
                + "\", \"password\":\""
                + password + "\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v2/token")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        response = response.replace("{\"access_token\": \"", "");
        String token = response.replace("\"}", "");

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }*/
}