package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    public List<RoomReservation> findByUser(User user);

    public List<RoomReservation> findByRoom(Room room);

    @Query(value = "SELECT * FROM room_reservation AS roomR "
            + "WHERE CAST(roomR.begin_time AS DATE) = :date AND roomR.room = :roomId",
            nativeQuery = true)
    public List<RoomReservation> findByRoomDate(@Param("date") String date, @Param("roomId") String roomId);
}
