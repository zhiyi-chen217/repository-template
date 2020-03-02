package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class RoomReservationController {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @GetMapping("roomReservations")
    public ResponseEntity getRoomReservation(@RequestParam Optional<String> user,
                                             @RequestParam Optional<String> room,
                                             @RequestParam Optional<String> date) {
        if (date.isPresent() && room.isPresent()) {
            return  ResponseEntity.status(200).body(roomReservationRepository.findByRoomDate(date.get(),
                    room.get()));
        }

        if (user.isPresent()) {
            User pretendUser = new User();
            pretendUser.setUserId(user.get());
            return ResponseEntity.status(200).body(roomReservationRepository.findByUser(pretendUser));
        }

        if (room.isPresent()) {
            Room pretendRoom = new Room();
            pretendRoom.setRoomId(room.get());
            return ResponseEntity.status(200).body(roomReservationRepository.findByRoom(pretendRoom));
        }

        return ResponseEntity.status(200).body(roomReservationRepository.findAll());
    }

    @PostMapping("roomReservation")
    public ResponseEntity postRoomReservation(@RequestBody RoomReservation roomReservation) {
        roomReservationRepository.save(roomReservation);
        return ResponseEntity.status(201).body("reservation saved");
    }

    @DeleteMapping("roomReservations")
    public ResponseEntity deleteRoomReservation(@RequestParam Long roomReservation) {
        roomReservationRepository.deleteById(roomReservation);
        return ResponseEntity.status(200).body("deleted");
    }
}
