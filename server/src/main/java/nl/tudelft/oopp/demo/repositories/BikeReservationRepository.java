package nl.tudelft.oopp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nl.tudelft.oopp.demo.entities.BikeReservation;

import java.util.Optional;

@Repository
public interface BikeReservationRepository extends JpaRepository<BikeReservation, String> {
    Optional<BikeReservation> findById(String Id);
}
