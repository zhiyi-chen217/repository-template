package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class HomepageController extends GeneralHomepageController {
    @FXML
    public void initialize() {
        setWelcomeMessage();
    }

    public void changeSceneRooms(ActionEvent event) throws IOException {
        changeScene(event, "/reserveARoomScene.fxml");
    }

    public void changeSceneBikes(ActionEvent event) throws IOException {
        changeScene(event, "/reserveBikeScene.fxml");
    }
}

