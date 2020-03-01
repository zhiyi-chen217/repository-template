package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class HomepageController extends GeneralHomepageController {

    @FXML private TableView<RoomReservation> roomReservationTable;
    @FXML private TableColumn<RoomReservation, LocalDateTime> beginTimeColumn;
    @FXML private TableColumn<RoomReservation, LocalDateTime> endTimeColumn;
    @FXML private TableColumn<RoomReservation, Building> buildingColumn;
    @FXML private TableColumn<RoomReservation, Room> roomColumn;

    @FXML
    public void initialize() throws IOException, URISyntaxException {
        setWelcomeMessage();
        endTimeColumn.setCellValueFactory(
                new PropertyValueFactory<RoomReservation, LocalDateTime>("endTime"));
        beginTimeColumn.setCellValueFactory(
                new PropertyValueFactory<RoomReservation, LocalDateTime>("beginTime"));
        buildingColumn.setCellValueFactory(
                new PropertyValueFactory<RoomReservation, Building>("building"));
        roomColumn.setCellValueFactory(
                new PropertyValueFactory<RoomReservation, Room>("room"));
        roomReservationTable.setItems(getReservations());
    }

    public ObservableList<RoomReservation> getReservations()
            throws IOException, URISyntaxException {
        CloseableHttpResponse response = ServerCommunication.readRoomReservation(getUsername(), null);
        return jsonArrayToReservation(response);
    }

    public void changeSceneRooms(ActionEvent event) {
        changeScene(event, "/reserveARoomScene.fxml");
    }

    public void changeSceneBikes(ActionEvent event) {
        changeScene(event, "/reserveBikeScene.fxml");
    }
}

