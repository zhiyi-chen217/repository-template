package nl.tudelft.oopp.demo.repositories;


import nl.tudelft.oopp.demo.entities.BikeReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BikeReservationRepository extends JpaRepository<BikeReservation, Long> {
}
