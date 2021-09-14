package by.akimova.CartAPI.security.jwt;

import by.akimova.CartAPI.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class JwtTokenProviderTest {

    @Autowired
    private MockMvc mockMvc;


 /*   @Test
    public void createToken() throws Exception {
        String token = JwtTokenProvider.createToken("john", Role.ADMIN.name());

        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/test").header("Authorization", token)).andExpect(status().isOk());
    }



    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test")).andExpect(status().isForbidden());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String token = JwtTokenProvider.createToken("john", Role.ADMIN.name());

        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/test").header("Authorization", token)).andExpect(status().isOk());
    }*/


}
