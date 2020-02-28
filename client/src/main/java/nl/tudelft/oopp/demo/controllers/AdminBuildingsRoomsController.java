package nl.tudelft.oopp.demo.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class AdminBuildingsRoomsController extends GeneralHomepageController {

    @FXML private Button editBuildingButton;
    @FXML private Button addBuildingButton;
    @FXML private Button deleteBuildingButton;
    @FXML private Button addRoomButton;
    @FXML private Button editRoomButton;
    @FXML private Button deleteRoomButton;
    @FXML private ListView<String> roomListView;
    @FXML private ChoiceBox<Building> buildingChoiceBox;
    private static ObservableList<Building> buildings = FXCollections.observableArrayList();
    private static ObservableList<Room> rooms = FXCollections.observableArrayList();

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
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void stageAddBuilding() throws IOException {
        newStage("/addABuilding.fxml");
    }

    public void stageAddRoom() throws IOException {
        newStage("/addARoomScene.fxml");
    }

    public void stageEditRoom() throws IOException {
        newStage("/editRoomScene.fxml");
    }

    public void stageEditBuilding() throws IOException {
        newStage("/editBuildingScene.fxml");
    }

    public void changeSelectedEvent(Observable v, Building oldBuilding, Building newBuilding)
            throws IOException, URISyntaxException {
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