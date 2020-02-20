package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginSceneController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void submitClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);
        alert.setContentText(ServerCommunication.login(username.getText(), password.getText()));
//        String result = "";
//        result += username.getText() + "\n" + password.getText();
//        alert.setContentText(result);
        alert.showAndWait();
    }
}
