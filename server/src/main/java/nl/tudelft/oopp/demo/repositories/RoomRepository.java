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

    List<Room> findByBuilding(Building building);

    @Query(value = "SELECT * FROM room WHERE "
            + "room.whiteboard IN (true, :whiteboard) AND room.tv IN (true, :tv) AND room.type "
            + "IN ('ALL_CAN_USE', :type) AND room.building_name = :buildingName "
            + "AND room.capacity >= :capacity", nativeQuery = true)
    public List<Room> filter(@Param("whiteboard") boolean whiteboard,
                             @Param("tv") boolean tv, @Param("type") String type,
                             @Param("buildingName") String buildingName, @Param("capacity") int capacity);

    /* // Employee queries
    public List<Room> findByTvTrueAndWhiteboardTrue();

    public List<Room> findByWhiteboardTrue();

    public List<Room> findByTvTrue();

    // Student queries
    public List<Room> findByTvTrueAndWhiteboardTrueAndType(String type);

    public List<Room> findByWhiteboardTrueAndType(String type);

    public List<Room> findByTvTrueAndType(String type); */
}
