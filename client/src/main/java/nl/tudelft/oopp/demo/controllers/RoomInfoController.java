package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RoomInfoController {

    @FXML
    private Label roomDescription;

    @FXML
    private Label roomCapacity;

    @FXML
    private Label roomTV;

    @FXML
    private Label roomWhiteboard;

    @FXML
    private Label roomType;

    @FXML
    private ListView<String> timeSlotList;

    public static String roomS;

    public static List<RoomReservation> roomReservationList;

    public static void setRoom (String roomString) {
        RoomInfoController.roomS = roomString;
    }

    public void initialize() throws IOException, URISyntaxException {
        String roomId = roomS.split("--")[0];
        Room room = (Room) GeneralHomepageController
                .JsonToEntity(ServerCommunication.readRoom(roomId, null), "Room");
        roomDescription.setText("Description: " + room.getDescription());
        roomCapacity.setText("Capacity:" + String.valueOf(room.getCapacity()));
        if(room.isTv()) {roomTV.setText("TV: available");}
        if(room.isWhiteboard()) {roomWhiteboard.setText("Whiteboard: available");}
        roomType.setText("Type: " + room.getType());

        LocalTime openingHour = room.getBuilding().getOpeningHour();
        LocalTime closingHour = room.getBuilding().getClosingHour();
        LocalTime beginSlot = openingHour;
        long numTimeSlot = ChronoUnit.HOURS.between(openingHour, closingHour);
        ObservableList<String> timeSlots = FXCollections.observableArrayList();
        for (int i = 0; i < numTimeSlot - 1; i++) {
            timeSlots.add(beginSlot.toString() + "--" + beginSlot.plusHours(1).toString());
            beginSlot = beginSlot.plusHours(1);
        }
        timeSlots.add(beginSlot.toString() + "--" + closingHour.toString());
        timeSlotList.setItems(timeSlots);
    }
}
