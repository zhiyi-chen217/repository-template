package nl.tudelft.oopp.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nl.tudelft.oopp.demo.entities.RoomReservation;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

}
