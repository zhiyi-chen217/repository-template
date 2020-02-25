package nl.tudelft.oopp.demo.controllers;


import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.exceptions.InvalidforeginkeyException;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BuildingRepository buildingRepository;


    @PostMapping("admin/room")
    public ResponseEntity createRoom(@RequestBody Room room) throws InvalidforeginkeyException {
        Optional<Room> temp = roomRepository.findById(room.getRoomId());
        if(temp.isPresent()){
            return ResponseEntity.badRequest().body("The room already exists");
        }
        roomRepository.save(room);
        return  ResponseEntity.accepted().body("saved successfully");
    }

}
