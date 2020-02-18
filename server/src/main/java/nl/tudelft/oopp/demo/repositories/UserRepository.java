package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String>{
    Users findByUserId(String userId);
}
