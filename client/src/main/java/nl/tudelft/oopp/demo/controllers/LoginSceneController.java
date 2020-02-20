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
    private TextField textfield1;
    @FXML
    private TextField textfield2;

    public void submitClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);
        String result = "";
        result += textfield1.getText() + "\n" + textfield2.getText();
        alert.setContentText(result);
        ServerCommunication.sendLogin(textfield1.getText(), textfield2.getText());
        alert.showAndWait();
    }
}
