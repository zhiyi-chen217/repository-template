package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;

import java.io.IOException;
import java.net.URISyntaxException;


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
    private CheckBox roomTV;

    @FXML
    private CheckBox roomType;

    @FXML
    private CheckBox roomWhiteboard;

    @FXML
    private Button submit;

    @FXML
    private TextField roomID;

    public static Room room;

    public static void setRoom(Room room1){
        room = room1;
    }

    public void initialize() throws IOException, URISyntaxException {
        room = (Room) GeneralHomepageController
                .JsonToEntity(ServerCommunication
                        .readRoom(room.getRoomId(), null), "Room");
        roomCapacity.setText(String.valueOf(room.getCapacity()));
        roomDescription.setText(room.getDescription());
        roomName.setText(room.getName());
        roomID.setText(room.getRoomId());
        roomPicturePath.setText(room.getPicturesPath());
        roomTV.setSelected(room.isTv());
        roomWhiteboard.setSelected(room.isWhiteboard());
        roomType.setSelected(room.getType().equals("Employee"));
    }

    public void submitRoom() throws IOException {
        String roomType = "ALL_CAN_USE";
        int roomCapacity = Integer.parseInt(this.roomCapacity.getText());
        String roomDescription = this.roomDescription.getText();
        String roomName = this.roomName.getText();
        String roomPicturePath = this.roomPicturePath.getText();
        Boolean roomTV = this.roomTV.isSelected();
        Boolean roomWhiteboard = this.roomWhiteboard.isSelected();
        if (this.roomType.isSelected()) { roomType = "Employee"; }
        ServerCommunication.updateRoom(room.getRoomId(), roomName, roomCapacity, room.getBuildingName(),
                roomDescription, roomType, roomPicturePath, roomWhiteboard, roomTV);
    }

}
