package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.entities.Room;


public class EditRoomController {
    @FXML
    private TextField roomCapacity;

    @FXML
    private TextField roomDescription;

    @FXML
    private TextField roomName;

    @FXML
    private TextField roomPicturePath;

    @FXML
    private TextField roomTV;

    @FXML
    private TextField roomType;

    @FXML
    private TextField roomWhiteboard;

    public static Room room;

    public static void setRoom(Room room1){
        room = room1;
    }

    public void initialize(){
        roomCapacity.setText(String.valueOf(room.getCapacity()));
        roomDescription.setText(room.getDescription());
        roomName.setText(room.getName());
        roomPicturePath.setText(room.getPicturesPath());
    }
}
