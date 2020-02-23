package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomepageController {
    @FXML
    private Label welcomeLabel;

    public void setWelcomeMessage(String message) {
        welcomeLabel.setText("Welcome " + message);
    }
}

