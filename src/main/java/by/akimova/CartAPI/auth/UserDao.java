package by.akimova.CartAPI.auth;

import java.util.Optional;

public interface UserDao {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
