package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainSceneController {

    @FXML
    private Button signUp;
    /**
     * Handles clicking the button.
     */

    public void btnClicked(ActionEvent event) throws IOException {
        Parent loginPageParent;
        if (event.getSource().equals(signUp)) {
            loginPageParent = FXMLLoader.load(getClass().getResource("/signUpScene.fxml"));
        } else {
            loginPageParent = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
        }
        if (loginPageParent != null) {
            //Get current stage
            Scene loginPageScene = new Scene(loginPageParent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginPageScene);
            stage.setTitle("TU Delft Campus Reservation System");
            stage.getIcons().add(new Image("https://simchavos.com/tu.png"));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred, please try again.");
        }
    }
}
