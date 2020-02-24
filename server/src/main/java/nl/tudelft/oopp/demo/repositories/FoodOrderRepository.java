package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, String> {
    Optional<FoodOrder> findById(String Id);
}
