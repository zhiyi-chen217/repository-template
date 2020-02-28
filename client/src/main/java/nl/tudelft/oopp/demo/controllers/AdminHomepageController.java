package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminHomepageController extends GeneralHomepageController {
    public void changeSceneBuildings(ActionEvent event) {
        changeScene(event, "/adminBuildingsRoomsScene.fxml");
    }

    @FXML
    public void initialize() {
        setWelcomeMessage();
    }
}
