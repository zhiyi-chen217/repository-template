package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String>{
    Optional<Users> findByUserId(String userId);
}
