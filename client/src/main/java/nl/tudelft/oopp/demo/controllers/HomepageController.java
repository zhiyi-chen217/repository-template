package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.RoomReservation;

import java.io.IOException;
import java.net.URISyntaxException;

public class HomepageController extends GeneralHomepageController {
    public static ObservableList<RoomReservation> roomReservations;

    @FXML
    private TableView<RoomReservation> reservationTable;

    @FXML
    private TableColumn<RoomReservation, String> roomColumn;

    @FXML
    private TableColumn<RoomReservation, String> timeSlotColumn;


    public void initialize() throws IOException, URISyntaxException {
        setWelcomeMessage();
        roomReservations = GeneralHomepageController
                .JsonArrayToRoomReservation(ServerCommunication
                        .readRoomReservation(ServerCommunication.userId, null, null));
        reservationTable.setItems(roomReservations);
        System.out.println(reservationTable.getColumns());
        roomColumn.setCellValueFactory(new PropertyValueFactory<RoomReservation, String>("roomString"));
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<RoomReservation, String>("timeSlot"));
    }

    public void changeSceneRooms(ActionEvent event) {
        changeScene(event, "/reserveARoomScene.fxml");
    }

    public void changeSceneBikes(ActionEvent event) {
        changeScene(event, "/reserveBikeScene.fxml");
    }
}

