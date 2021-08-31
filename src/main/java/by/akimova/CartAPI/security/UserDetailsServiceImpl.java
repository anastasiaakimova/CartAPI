package by.akimova.CartAPI.security;

import by.akimova.CartAPI.model.User;
import by.akimova.CartAPI.repository.UserRepository;
import by.akimova.CartAPI.security.jwt.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link org.springframework.security.core.userdetails.UserDetailsService} interface.
 *
 * @author anastasiaakimova
 * @version 1.0
 */
@Slf4j
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        log.info("IN loadUserByUserNAme - user with username (mail): {} successfully loaded", mail);
        return SecurityUser.fromUser(user);
    }
}
