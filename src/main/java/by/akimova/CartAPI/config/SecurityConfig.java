package by.akimova.CartAPI.config;

import by.akimova.CartAPI.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/cart").hasRole(Role.USER.name())
                .antMatchers("/users").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/items").permitAll()
                .antMatchers(HttpMethod.POST, "/items").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/items").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/items").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/items").hasRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(

        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
