package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginSceneController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    /**
     * Submit the login credentials when clicked on submit
     */
    public void submitClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.sendLogin(username.getText(), password.getText()));
        alert.showAndWait();
    }

    /**
     * This was an attempt to retrieve the admin info via getQuote() of the serverCommunication
     */
    public void adminClicked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.getQuote());
        alert.showAndWait();
    }
}
