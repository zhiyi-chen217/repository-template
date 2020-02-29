package nl.tudelft.oopp.demo.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminBuildingsRoomsController extends GeneralHomepageController {

    @FXML private Button editBuildingButton;
    @FXML private Button addBuildingButton;
    @FXML private Button deleteBuildingButton;
    @FXML private Button addRoomButton;
    @FXML private Button editRoomButton;
    @FXML private Button deleteRoomButton;
    @FXML private ListView<String> roomListView;
    @FXML private ChoiceBox<Building> buildingChoiceBox;

    private static ObservableList<Building> buildings;
    private static ObservableList<Room> rooms;

    /**
     * Initialization method that is run when the scene is loading.
     */
    @FXML
    public void initialize() throws IOException, URISyntaxException {
        editBuildingButton.setDisable(true);
        deleteBuildingButton.setVisible(false);
        editRoomButton.setVisible(false);
        deleteRoomButton.setVisible(false);
        roomListView.setVisible(false);
        buildings = GeneralHomepageController
                .JsonArrayToBuilding(ServerCommunication.readBuilding(null));
        buildingChoiceBox.getItems().setAll(buildings);
        buildingChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldBuilding, newBuilding) -> {
                    try {
                        changeSelectedEvent(v, oldBuilding, newBuilding);
                        roomListView.setVisible(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void stageAddBuilding() {
        newStage("/addABuilding.fxml", addBuildingButton);
    }

    public void stageAddRoom() {
        newStage("/addARoomScene.fxml", addRoomButton);
    }

    public void stageEditRoom() throws IOException {
        try {
            String temp = roomListView.getSelectionModel().getSelectedItem().split("--")[0];
            Room selected = rooms.stream().filter(s -> s.getRoomId().equals(temp))
                    .collect(Collectors.toList()).get(0);
            EditRoomController.setRoom(selected);
            newStage("/editRoomScene.fxml", editRoomButton);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void stageEditBuilding() throws IOException {
        Building building = buildingChoiceBox.getValue();
        EditBuildingController.setBuilding(building);
        newStage("/editBuildingScene.fxml", editBuildingButton);
    }

    /** Once a building is selected, the scene is somewhat changed.
     *
     * @param event The event which calls this function
     */
    public void selectBuilding(ActionEvent event) {
        editBuildingButton.setDisable(false);
        deleteBuildingButton.setVisible(true);
        editRoomButton.setVisible(true);
        deleteRoomButton.setVisible(true);
        roomListView.setVisible(true);
    }

    public void deleteBuilding() {
        Building building = buildingChoiceBox.getValue();
        String name = building.getName();
        Alert alert;
        try {
            CloseableHttpResponse response = ServerCommunication.deleteBuilding(name);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(EntityUtils.toString(response.getEntity(), "UTF-8"));
            if (response.getStatusLine().getStatusCode() == 202) {
                ObservableList<Building> tempB = buildingChoiceBox.getItems();
                tempB.remove(building);
                buildings.remove(building);
                buildingChoiceBox.setItems(tempB);
                ObservableList<String> tempR = roomListView.getItems();
                tempR.removeAll();
                roomListView.setItems(tempR);
            }
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong, please try again.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
            return;
        }
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void changeSelectedEvent(Observable v, Building oldBuilding, Building newBuilding)
            throws IOException, URISyntaxException {
        rooms = GeneralHomepageController
                .JsonArrayToRoom(ServerCommunication.readRoom(null, newBuilding.getName()));
        ObservableList<String> allRoom = FXCollections.observableArrayList();
        for (int i = 0; i < rooms.size(); i++) {
            Room temp = rooms.get(i);
            allRoom.add(temp.getRoomId() + "--" + temp.getName());
        }
        roomListView.setItems(allRoom);

    }

    public void deleteRoom() {
        String listString = roomListView.getSelectionModel().getSelectedItem();
        String roomId = listString.split("--")[0];
        Room selected = rooms.stream().filter(s -> s.getRoomId().equals(roomId))
                .collect(Collectors.toList()).get(0);
        Alert alert;
        try {
            CloseableHttpResponse response = ServerCommunication.deleteRoom(List.of(selected.getRoomId()));
            alert = new Alert(Alert.AlertType.CONFIRMATION,
                    EntityUtils.toString(response.getEntity()),
                    ButtonType.OK);
            if (response.getStatusLine().getStatusCode() == 202) {
                ObservableList<String> temp = roomListView.getItems();
                temp.remove(listString);
                roomListView.setItems(temp);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong, please try again.");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
            return;
        }
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void confirmationDeletion(ActionEvent event) {
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "Are you sure you want to delete this?",
                        ButtonType.OK,
                        ButtonType.CANCEL);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (event.getSource().equals(deleteRoomButton)) {
                deleteRoom();
            } else if (event.getSource().equals(deleteBuildingButton)) {
                deleteBuilding();
            }
        }
    }

}