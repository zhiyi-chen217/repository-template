package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.TimeSlotCell;
import nl.tudelft.oopp.demo.entities.TimeSlotCellFactory;
import org.apache.http.client.methods.CloseableHttpResponse;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RoomInfoController extends GeneralHomepageController{

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

    public static ObservableList<RoomReservation> roomReservations;

    public static void setRoom (String roomString) {
        RoomInfoController.roomS = roomString;
    }

    public void initialize() throws IOException, URISyntaxException {
        String roomId = roomS.split("--")[0];
        Room room = (Room) GeneralHomepageController
                .JsonToEntity(ServerCommunication.readRoom(roomId, null), "Room");
        roomReservations = GeneralHomepageController
                .JsonArrayToRoomReservation(ServerCommunication
                        .readRoomReservation(ServerCommunication.userId, roomId, LocalDate.now().toString()));
        TimeSlotCell.roomReservations = roomReservations;

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
        System.out.println(timeSlots.get(0));
        timeSlotList.setCellFactory(new TimeSlotCellFactory());
    }

    public void confirmReservation(ActionEvent event) throws IOException {
        ObservableList<String> reservations = timeSlotList.getSelectionModel().getSelectedItems();
        LocalTime beginTime, endTime;
        LocalDate today = LocalDate.now();
        for (String s: reservations) {
            beginTime = LocalTime.parse(s.split("--")[0]);
            endTime = LocalTime.parse(s.split("--")[1]);
            CloseableHttpResponse response =
                    ServerCommunication.createRoomReservation(ServerCommunication.userId,
                            LocalDateTime.of(today, beginTime), LocalDateTime.of(today, endTime),
                            roomS.split("--")[0]);
            if (response.getStatusLine().getStatusCode() != 201) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Reservation: " + s + "fail");
                alert.showAndWait();
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Reservation completed");
        alert.showAndWait();
    }

    public void changeSceneReserveARoom(ActionEvent event) {
        changeScene(event, "/reserveARoomScene.fxml");
    }

}
