package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, String> {
    Optional<RoomReservation> findById(String Id);
}
