package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for class {@link User}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository("userRepository")
public interface UserRepository extends MongoRepository<User, String> {
    void deleteUserByUserId(UUID userId);

    Optional<User> findByMail(String mail);

    User findUserByUserId(UUID userId);

}
