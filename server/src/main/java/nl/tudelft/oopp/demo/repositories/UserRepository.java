package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByUserId(String userId);
}
