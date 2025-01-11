package pl.wsb.flashlearn.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wsb.flashlearn.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    void deleteUserByUsername(String username);
}
