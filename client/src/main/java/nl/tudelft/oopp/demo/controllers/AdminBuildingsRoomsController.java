package nl.tudelft.oopp.demo.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ChangeListener;
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
    @FXML private Button refreshButton;

    private static ObservableList<Building> buildings;

    private static ObservableList<Room> rooms;

    /**
     * Initialization method that is run when the scene is loading.
     */
    @FXML
    public void initialize() throws IOException, URISyntaxException {
        disableNodes();
        roomListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        CloseableHttpResponse response = BuildingServerCommunication.readBuilding(null);
        if (response == null) {
            errorAlert();
            return;
        }
        buildings = GeneralHomepageController
                .jsonArrayToBuilding(response);
        buildingChoiceBox.setItems(buildings);

        buildings.addListener((ListChangeListener<Building>) c -> {
            buildingChoiceBox.setItems(buildings);
        });

        buildingChoiceBox.getSelectionModel().selectedItemProperty().addListener(
            (v, oldBuilding, newBuilding) -> {
                try {
                    changeSelectedEvent(v, oldBuilding, newBuilding);
                    roomListView.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong, please try again.");
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.showAndWait();
                }
            }
        );
    }

    public void stageAddBuilding() {
        newStage("/addABuilding.fxml", addBuildingButton);
    }

    /**Constructs the AddRoom stage.
     *
     */
    public void stageAddRoom() {
        Building building = buildingChoiceBox.getValue();
        AddARoomController.setBuilding(building);
        newStage("/addARoomScene.fxml", addRoomButton);
    }

    /**
     * This method sets up the EditRoom page and change to that page.
     */
    public void stageEditRoom() {
        try {
            String temp = roomListView.getSelectionModel().getSelectedItem().split("--")[0];
            Room selected = rooms.stream().filter(s -> s.getRoomId().equals(temp))
                    .collect(Collectors.toList()).get(0);
            EditRoomController.setRoom(selected);
            newStage("/editRoomScene.fxml", editRoomButton);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make a selection");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    /**
     * This method sets up the EditBuilding page and change to that page.
     */
    public void stageEditBuilding() {
        Building building = buildingChoiceBox.getValue();
        EditBuildingController.setBuilding(building);
        newStage("/editBuildingScene.fxml", editBuildingButton);
    }

    /** Once a building is selected, the scene is somewhat changed.
     *
     * @param event The event which calls this function
     */
    public void selectBuilding(ActionEvent event) {
        if (editBuildingButton.isDisable()) {
            editBuildingButton.setDisable(false);
            deleteBuildingButton.setVisible(true);
            editRoomButton.setVisible(true);
            deleteRoomButton.setVisible(true);
            roomListView.setVisible(true);
            addRoomButton.setDisable(false);
            refreshButton.setVisible(true);
        }
    }

    /**Deletes the building the user has selected.
     */
    public void deleteBuilding() {
        Building building = buildingChoiceBox.getValue();
        String name = building.getName();
        Alert alert;
        try {
            CloseableHttpResponse response = BuildingServerCommunication.deleteBuilding(name);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            if (response == null) {
                errorAlert();
                return;
            }
            alert.setContentText(EntityUtils.toString(response.getEntity(), "UTF-8"));

            if (response.getStatusLine().getStatusCode() == 200) {
                buildings.remove(building);
                roomListView.setItems(null);
                disableNodes();
            }
        } catch (Exception e) {
            errorAlert();
            return;
        }
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**Not sure how to do this javadoc.
     * @param v is observable
     * @param oldBuilding is the old building
     * @param newBuilding is the new building
     * @throws IOException if it can not find the building
     */
    public void changeSelectedEvent(Observable v, Building oldBuilding, Building newBuilding)
            throws IOException {
        if (newBuilding == null) {
            return;
        }
        rooms = GeneralHomepageController
                .jsonArrayToRoom(RoomServerCommunication.readRoom(null, newBuilding.getName()));
        ObservableList<String> allRoom = FXCollections.observableArrayList();
        for (Room temp : rooms) {
            allRoom.add(temp.getRoomId() + "--" + temp.getName());
        }
        roomListView.setItems(allRoom);
    }

    /**
     * This method first pops an alert if not all requirements for deleting a room are fulfilled.
     * Then it tries to delete all the selected rooms, an alert is popped afterwards,
     * indicating whether the deletion is successful.
     */
    public void deleteRoom() {
        ObservableList<String> selectedRoomNames = roomListView.getSelectionModel().getSelectedItems();
        if (selectedRoomNames.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make a selection");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        List<String> selectedRooms = new ArrayList<>();
        for (String s: selectedRoomNames) {
            String roomId = s.split("--")[0];
            selectedRooms.add(roomId);
        }
        Alert alert;
        try {
            CloseableHttpResponse response = RoomServerCommunication.deleteRoom(selectedRooms);
            alert = new Alert(Alert.AlertType.CONFIRMATION,
                    EntityUtils.toString(response.getEntity()),
                    ButtonType.OK);
            if (response.getStatusLine().getStatusCode() == 200) {
                ObservableList<String> temp = roomListView.getItems();
                temp.removeAll(selectedRoomNames);
                roomListView.setItems(temp);
            }
        } catch (IOException e) {
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

    /** This method asks for confirmation when a request
     * has been sent for deletion of a room or building.
     * @param event the event which triggers the execution of this method
     */
    public void confirmationDeletion(ActionEvent event) {
        String content = "Are you sure you want to delete this?";
        if (event.getSource().equals(deleteRoomButton)) {
            content = "Are you sure you want to delete this room?";
        } else if (event.getSource().equals(deleteBuildingButton)) {
            content = "All the rooms in this building will be deleted too. \n "
                    + "Are you sure you want to delete this building? ";
        }
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        content,
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

    /** Disables the nodes that should only be accessed after
     * a building has been selected.
     */
    public void disableNodes() {
        if (!editBuildingButton.isDisable()) {
            editBuildingButton.setDisable(true);
            deleteBuildingButton.setVisible(false);
            editRoomButton.setVisible(false);
            deleteRoomButton.setVisible(false);
            roomListView.setVisible(false);
            addRoomButton.setDisable(true);
            refreshButton.setVisible(false);
        }
    }

    public void refresh(ActionEvent event) throws IOException {
        changeSelectedEvent(null, null, buildingChoiceBox.getSelectionModel().getSelectedItem());
    }

    public static ObservableList<Building> getBuildings() {
        return buildings;
    }

}