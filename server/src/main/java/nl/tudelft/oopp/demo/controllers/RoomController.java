package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.exceptions.InvalidforeginkeyException;
import nl.tudelft.oopp.demo.exceptions.RedundantentityException;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * This class contains Rest endpoints for CRUD operations for Room entity.
 */
@RestController
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;


    /**
     * This method saves the room if it has no invalid fields,
     * or it returns an bad request Http response.
     * @param room the room to be saved
     * @return the generated ResponseEntity
     * @throws InvalidforeginkeyException the referenced building is not found
     */
    @PostMapping("admin/room")
    public ResponseEntity createRoom(@RequestBody Room room) throws InvalidforeginkeyException,
            RedundantentityException {
        try {
            Optional<Room> temp = roomRepository.findById(room.getRoomId());
            if (temp.isPresent()) {
                throw new RedundantentityException("The room already exists");
            }
            roomRepository.save(room);
            return ResponseEntity.status(201).body("saved successfully");
        } catch (JpaObjectRetrievalFailureException e) {
            throw new InvalidforeginkeyException("The referenced building does not exist.");
        }
    }


    /**
     * The method receives a room and tries to update it.
     * @param room the room to be updated
     * @return a ResponseEntity with info about the updated room
     * @throws EntityNotFoundException if the room is not found
     */

    @PutMapping("admin/room")
    public ResponseEntity updateRoom(@RequestBody Room room) throws
            EntityNotFoundException {
        Optional<Room> temp = roomRepository.findById(room.getRoomId());
        if (temp.isEmpty()) {
            throw new EntityNotFoundException("The room does not exist!");
        }
        roomRepository.save(room);
        return ResponseEntity.status(201).body("updated successfully!");
    }

    /**
     * This method receives a roomId
     * and tries to find then room with the roomId.
     * @param roomId the id of the
     * @return a ResponseEntity to be sent back wit info about the room
     */
    @GetMapping("rooms")
    public ResponseEntity readRoom(@RequestParam Optional<String> roomId,
                                   @RequestParam Optional<String> building,
                                   @RequestParam Optional<String> roomName) {
        String roomType = "ALL_CAN_USE";
        List<GrantedAuthority> authorities = new ArrayList<>(SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities());
        if (authorities.contains(new SimpleGrantedAuthority("Employee"))
                || authorities.contains(new SimpleGrantedAuthority("Admin"))) {
            roomType = "Employee";
        }
        List<String> types = new ArrayList<String>(List.of("ALL_CAN_USE", roomType));

        if (roomName.isPresent() && building.isPresent()) {
            Building temp = new Building();
            temp.setName(building.get());
            return ResponseEntity.accepted().body(roomRepository
                    .findByNameContainingIgnoreCaseAndTypeInAndBuilding(roomName.get(), types, temp));
        }
        if (building.isPresent()) {
            Building temp = new Building();
            temp.setName(building.get());
            return ResponseEntity.accepted().body(roomRepository.findByBuildingAndTypeIn(temp, types));
        }
        if (roomId.isEmpty()) {
            return ResponseEntity.accepted().body(roomRepository.findAll());
        }
        Optional<Room> tempRoom = roomRepository.findByRoomId(roomId.get());
        if (tempRoom.isPresent()) {
            return ResponseEntity.status(200).body(tempRoom.get());
        }
        return ResponseEntity.badRequest().body("The room does not exist!");
    }

    /** Returns a list of rooms based on the filters specified in the parameters.
     * @param buildingName the name of the building
     * @param whiteboard a boolean value describing the availability of whiteboards in a room
     * @param tv a boolean value describing the availability of TVs in a room
     * @param capacity the minimum capacity of a room
     * @return a ResponseEntity to be sent back
     */
    @GetMapping("rooms/filter")
    public ResponseEntity readRoomFilter(
                                   @RequestParam String buildingName,
                                   @RequestParam boolean whiteboard,
                                   @RequestParam boolean tv,
                                   @RequestParam int capacity) {
        String roomType = "ALL_CAN_USE";
        if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority("Employee"))) {
            roomType = "Employee";
        }
        return ResponseEntity.accepted().body(roomRepository.filter(whiteboard,
                tv, roomType, buildingName, capacity));
    }


    /**
     * This method receives a list of roomId
     * and tries to delete all rooms with id in the list.
     * @param roomIds a list of id of the rooms to be deleted
     * @return a ResponseEntity to send back to the client with info about the deleted rooms
     */
    @DeleteMapping("admin/room")
    public ResponseEntity deleteRoom(@RequestParam List<String> roomIds) {
        try {
            for (String roomId : roomIds) {
                roomRepository.deleteById(roomId);
            }
            return ResponseEntity.status(200).body("All deleted!");
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Cannot delete non-existing room",1);
        }
    }
}
