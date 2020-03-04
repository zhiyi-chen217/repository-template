package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByRoomId(String roomId);

    List<Room> findByBuildingAndTypeIn(Building building, List<String> types);

    List<Room> findByNameContainingIgnoreCaseAndTypeIn(String name, List<String> types);

    @Query(value = "SELECT * FROM room WHERE "
            + "room.whiteboard IN (true, :whiteboard) AND room.tv IN (true, :tv) AND room.type "
            + "IN ('ALL_CAN_USE', :type) AND room.building_name = :buildingName "
            + "AND room.capacity >= :capacity", nativeQuery = true)
    public List<Room> filter(@Param("whiteboard") boolean whiteboard,
                             @Param("tv") boolean tv, @Param("type") String type,
                             @Param("buildingName") String buildingName, @Param("capacity") int capacity);
}
