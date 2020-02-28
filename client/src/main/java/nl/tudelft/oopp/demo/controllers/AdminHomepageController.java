package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomepageController extends GeneralHomepageController {
    public void changeSceneBuildings(ActionEvent event) throws IOException {
        changeScene(event, "/adminBuildingsRoomsScene.fxml");
    }

    @FXML
    public void initialize() {
        setWelcomeMessage();
    }
}
