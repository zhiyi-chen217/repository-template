package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;


public class LoginSceneController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    /**
     * Submit the login credentials when clicked on submit.
     */
    public void submitClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);
        String usertxt = username.getText();
        String pwtxt = password.getText();
        alert.setContentText(ServerCommunication.sendLoginUser(usertxt, pwtxt));
        alert.showAndWait();
    }

    /**
     * When this method is called, the scene gets changed to the homepage.
     */


    /**
     * Submit the login credentials of an admin when clicked on submit.
     */
    public void adminClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);
        String usertxt = username.getText();
        String pwtxt = password.getText();
        alert.setContentText(ServerCommunication.sendLoginAdmin(usertxt, pwtxt));
        alert.showAndWait();
    }
}
