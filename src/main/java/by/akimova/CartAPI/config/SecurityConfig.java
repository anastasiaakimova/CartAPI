package by.akimova.CartAPI.config;

import by.akimova.CartAPI.model.Permission;
import by.akimova.CartAPI.model.Role;
import by.akimova.CartAPI.security.JwtConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/carts").hasRole(Role.USER.name())
                .antMatchers("/users").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/items").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/items").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/items").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PATCH, "/items").hasAuthority(Permission.USER_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/items").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
