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
        buildings = FXCollections.observableArrayList();
        JSONArray jsonArray = new JSONArray(EntityUtils.toString(ServerCommunication.readBuilding(null)
                                        .getEntity()));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            buildings.add(new Building(temp));
        }
        buildingChoiceBox.getItems().setAll(buildings);
        buildingChoiceBox.getSelectionModel().selectedItemProperty().addListener(
            (v, oldBuilding, newBuilding) -> {
                try {
                    changeSelectedEvent(v, oldBuilding, newBuilding);
                    roomListView.setVisible(true);
                } catch (IOException | URISyntaxException e) {
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

    public void stageAddRoom() {
        Building building = buildingChoiceBox.getValue();
        AddARoomController.setBuilding(building);
        newStage("/addARoomScene.fxml", addRoomButton);
    }

    public void stageEditRoom() {
        String temp = roomListView.getSelectionModel().getSelectedItem();
        Room selected = rooms.stream().filter(s -> s.getName().equals(temp))
                .collect(Collectors.toList()).get(0);
        EditRoomController.setRoom(selected);
        newStage("/editRoomScene.fxml", editRoomButton);
    }

    public void stageEditBuilding() {
        Building building = buildingChoiceBox.getValue();
        EditBuildingController.setBuilding(building);

//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/editBuildingScene.fxml"));
//        Parent homePageParent = loader.load();
//        Scene homePageScene = new Scene(homePageParent);
//
//        //Get current stage
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(homePageScene);
//        stage.getIcons().add(new Image("https://simchavos.com/tu.png"));
//        stage.show();
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
        List<String> list = new ArrayList<>();
        list.add(name);

        Alert alert;
        try {
            CloseableHttpResponse response = ServerCommunication.deleteBuilding(list);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(EntityUtils.toString(response.getEntity(), "UTF-8"));
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
        rooms = FXCollections.observableArrayList();
        JSONArray jsonArray = new JSONArray(EntityUtils.toString(ServerCommunication
                .readRoom(null, newBuilding.getName()).getEntity()));
        ObservableList<String> allRoom = FXCollections.observableArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            allRoom.add(temp.getString("name"));
            rooms.add(new Room(temp));
        }
        roomListView.setItems(allRoom);

    }
}