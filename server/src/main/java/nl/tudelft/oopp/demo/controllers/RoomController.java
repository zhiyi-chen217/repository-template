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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
            return ResponseEntity.accepted().body("saved successfully");
        }
        catch (JpaObjectRetrievalFailureException e){
            throw new InvalidforeginkeyException("The referenced building does not exist.");
        }
    }


    /**
     * The method receives a room and tries to update it.
     * @param room
     * @return
     * @throws EntityNotFoundException
     */
    @PutMapping("admin/room")
    public ResponseEntity updateRoom(@RequestBody Room room) throws
            EntityNotFoundException {
        Optional<Room> temp = roomRepository.findById(room.getRoomId());
        if(temp.isEmpty()){
            throw new EntityNotFoundException("The room does not exist!");
        }
        roomRepository.save(room);
        return ResponseEntity.accepted().body("updated successfully!");
    }

    /**
     * This method receives a roomId
     * and tries to find then room with the roomId.
     * @param roomId
     * @return
     */
    @GetMapping("rooms")
    public ResponseEntity readRoom(@RequestParam Optional<String> roomId, @RequestParam Optional<String> building){
        if(building.isPresent()){
            Building temp = new Building();
            temp.setName(building.get());
            return ResponseEntity.accepted().body(roomRepository.findByBuilding(temp));
        }
        if(roomId.isEmpty()){
            return ResponseEntity.accepted().body(roomRepository.findAll());
        }
        Optional<Room> tempRoom = roomRepository.findByRoomId(roomId.get());
        if(tempRoom.isPresent()){
            return ResponseEntity.accepted().body(tempRoom);
        }
        return ResponseEntity.badRequest().body("The room does not exist!");
    }

    /**
     * This method receives a list of roomId
     * and tries to delete all rooms with id in the list.
     * @param roomIds
     * @return
     */
    @DeleteMapping("admin/room")
    public ResponseEntity deleteRoom(@RequestParam List<String> roomIds){
        try {
            for (String roomId : roomIds) {
                roomRepository.deleteById(roomId);
            }
            return ResponseEntity.accepted().body("All deleted!");
        }
        catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("Cannot delete non-existing room",1);
        }
    }
}
