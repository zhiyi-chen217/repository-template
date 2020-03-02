package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;


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
    private CheckBox roomTV;

    @FXML
    private CheckBox roomType;

    @FXML
    private CheckBox roomWhiteboard;

    @FXML
    private Label roomBuildingName;

    @FXML
    private Button submit;

    @FXML
    private Label roomID;

    @FXML
    private Label failText;

    public static Room room;

    public static void setRoom(Room room1) {
        room = room1;
    }

    public void initialize() throws IOException, URISyntaxException {
        room = (Room) GeneralHomepageController
                .JsonToEntity(ServerCommunication
                        .readRoom(room.getRoomId(), null), "Room");
        roomCapacity.setText(String.valueOf(room.getCapacity()));
        roomDescription.setText(room.getDescription());
        roomName.setText(room.getName());
        roomPicturePath.setText(room.getPicturesPath());
        roomBuildingName.setText(room.getBuildingName());
        roomID.setText(room.getRoomId());
        roomTV.setSelected(room.isTv());
        roomWhiteboard.setSelected(room.isWhiteboard());
        roomType.setSelected(room.getType().equals("Employee"));
    }

    /**Method that updates a room that the admin has inputted.
     * @throws IOException if the input is not appropriate
     */
    public void submitRoom() throws IOException {
        if (this.roomCapacity.getText().equals("") || this.roomDescription.getText().equals("")
                || this.roomName.getText().equals("") || this.roomPicturePath.getText().equals("")) {
            failText.setText("Please fill in all fields");
            return;
        }

        String roomType = "ALL_CAN_USE";
        String roomCap = this.roomCapacity.getText();
        if (!roomCap.matches("\\d+") ) {
            failText.setText("Please input a valid number");
            return;
        }

        int roomCapint = Integer.parseInt(roomCap);
        if (roomCapint == 0) {
            failText.setText("Please input a capacity more than 0");
            return;
        }

        String roomDescription = this.roomDescription.getText();
        String roomName = this.roomName.getText();
        String roomPicturePath = this.roomPicturePath.getText();
        boolean roomTV = this.roomTV.isSelected();
        boolean roomWhiteboard = this.roomWhiteboard.isSelected();
        if (this.roomType.isSelected()) {
            roomType = "Employee";
        }
        CloseableHttpResponse response = ServerCommunication.updateRoom(room.getRoomId(), roomName, roomCapint, room.getBuildingName(),
                roomDescription, roomType, roomPicturePath, roomWhiteboard, roomTV);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (response.getStatusLine().getStatusCode() == 201) {
            alert.setTitle("Success");
            alert.setContentText("Changes saved");
        } else {
            alert.setTitle("Fail");
            alert.setContentText("Changes cannot be saved");
        }
        alert.showAndWait();




//        try {
//            String roomType = "ALL_CAN_USE";
//            int roomCapacity = Integer.parseInt(this.roomCapacity.getText());
//            if( roomCapacity <= 0) {
//                throw new NumberFormatException("must be positive number");
//            }
//            String roomDescription = this.roomDescription.getText();
//            String roomName = this.roomName.getText();
//            String roomPicturePath = this.roomPicturePath.getText();
//            Boolean roomTV = this.roomTV.isSelected();
//            Boolean roomWhiteboard = this.roomWhiteboard.isSelected();
//            if (this.roomType.isSelected()) {
//                roomType = "Employee";
//            }
//            CloseableHttpResponse response = ServerCommunication.updateRoom(room.getRoomId(), roomName, roomCapacity, room.getBuildingName(),
//                    roomDescription, roomType, roomPicturePath, roomWhiteboard, roomTV);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText(null);
//            if (response.getStatusLine().getStatusCode() == 201) {
//                alert.setTitle("Success");
//                alert.setContentText("Changes saved");
//            } else {
//                alert.setTitle("Fail");
//                alert.setContentText("Changes cannot be saved");
//            }
//            alert.showAndWait();
//        } catch (NumberFormatException e) {
//            failText.setText("Please make sure the capacity is correct.");
//        }

    }

}
