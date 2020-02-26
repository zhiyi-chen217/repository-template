package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminBuildingsRoomsController extends GeneralHomepageController {
    public void changeSceneHomepage(ActionEvent event) throws IOException {
        changeScene(event, "/adminHomepageScene.fxml");
    }
    public void stageAddBuilding(ActionEvent event) throws IOException {
        newStage(event, "/addABuilding.fxml");
    }

}