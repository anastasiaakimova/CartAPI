package by.akimova.CartAPI.repository;

import by.akimova.CartAPI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository("userRepository")
public interface UserRepository extends MongoRepository<User, String> {
    void deleteById(UUID id);

    User findById(UUID id);

    Optional<User> findByMail(String mail);
}
