package nl.tudelft.oopp.demo.controllers;

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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

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
}