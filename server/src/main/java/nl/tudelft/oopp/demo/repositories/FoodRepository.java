package nl.tudelft.oopp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nl.tudelft.oopp.demo.entities.Food;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {
    Optional<Food> findByName(String name);
}
