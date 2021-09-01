package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Repository for User.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

public interface UserRepository extends MongoRepository<User, String> {
    void deleteByUserId(UUID userId);

    User findByUserId(UUID userId);
}
