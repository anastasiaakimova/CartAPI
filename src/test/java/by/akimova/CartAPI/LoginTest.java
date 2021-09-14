package by.akimova.CartAPI;

import by.akimova.CartAPI.rest.AuthenticationRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRestController authenticationRestController;

    /**
     * Trial test to improve skills.
     *
     * @throws Exception
     */
/*    @Test
    public void greetingTest() throws Exception {
        this.mockMvc.perform(get("/users/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, guest!")));
    }*/


    /**
     * Test correct login.
     *
     * @throws Exception
     */
   /* @Test
    public void correctLogin() throws Exception {
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("maryCo@gmail.com").password(passwordEncoder.encode("admin")))
                .andDo(print())
                .andExpect(status().isOk());
               // .andExpect(content().string(containsString("")));
    }*/

 /*   @Test
    public void badCredentials() throws Exception {
        this.mockMvc.perform(post("/auth/login").param("maryCo@gmail.com", "admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }*/
}
