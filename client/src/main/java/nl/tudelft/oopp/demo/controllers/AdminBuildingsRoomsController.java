package nl.tudelft.oopp.demo.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.io.IOException;

public class AdminBuildingsRoomsController extends GeneralHomepageController {

    @FXML private Button editBuildingButton;
    @FXML private Button addBuildingButton;
    @FXML private Button deleteBuildingButton;
    @FXML private Button addRoomButton;
    @FXML private Button editRoomButton;
    @FXML private Button deleteRoomButton;
    @FXML private ListView<String> roomListView;

    /**
     * Initialization method that is run when the scene is loading.
     */
    @FXML
    public void initialize() {
        editBuildingButton.setDisable(true);
        deleteBuildingButton.setVisible(false);
        editRoomButton.setVisible(false);
        deleteRoomButton.setVisible(false);
        roomListView.setVisible(false);
    }

    public void stageAddBuilding() {
        newStage("/addABuilding.fxml");
    }

    public void stageAddRoom() {
        newStage("/addARoomScene.fxml");
    }

    public void stageEditRoom() {
        newStage("/editRoomScene.fxml");
    }

    public void stageEditBuilding() {
        newStage("/editBuildingScene.fxml");
    }
}