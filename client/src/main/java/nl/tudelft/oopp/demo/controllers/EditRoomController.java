package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;

import java.io.IOException;


public class EditRoomController {
    @FXML
    private TextField roomCapacity;

    @FXML
    private TextArea roomDescription;

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

    @FXML
    private TextField roomBuildingName;

    @FXML
    private Button submit;

    public static Room room;

    public static void setRoom(Room room1) {
        room = room1;
    }

    public void initialize() {
        roomCapacity.setText(String.valueOf(room.getCapacity()));
        roomDescription.setText(room.getDescription());
        roomName.setText(room.getName());
        roomPicturePath.setText(room.getPicturesPath());
        roomBuildingName.setText(room.getBuildingName());
    }

    public void submitRoom() throws IOException {
        String buildingName = roomBuildingName.getText();
        int roomCapacity = Integer.parseInt(this.roomCapacity.getText());
        String roomDescription = this.roomDescription.getText();
        String roomName = this.roomName.getText();
        String roomPicturePath = this.roomPicturePath.getText();
        Boolean roomTV = false;
        Boolean roomWhiteboard = false;
        String roomType = "ALL_CAN_USE";
        ServerCommunication.updateRoom(room.getRoomId(), roomName, roomCapacity, buildingName,
                roomDescription, roomType, roomPicturePath, roomWhiteboard, roomTV);
    }
}
