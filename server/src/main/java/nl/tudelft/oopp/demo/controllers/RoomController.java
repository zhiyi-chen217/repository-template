package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.exceptions.InvalidforeginkeyException;
import nl.tudelft.oopp.demo.exceptions.RedundantentityException;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
        Optional<Room> temp = roomRepository.findById(room.getRoomId());
        if (temp.isPresent()) {
            throw new RedundantentityException("The room already exists");
        }
        roomRepository.save(room);
        return  ResponseEntity.accepted().body("saved successfully");
    }

}
