package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class HomepageController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutBtn;

    public void setWelcomeMessage(String message) {
        welcomeLabel.setText("Welcome " + message);
    }

    public void logout() {
        ServerCommunication.resetPubAuth();
    }
}

