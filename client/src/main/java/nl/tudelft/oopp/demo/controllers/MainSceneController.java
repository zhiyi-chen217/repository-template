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

    @FXML
    private Button login;

    /**
     * Handles clicking the button.
     */

    public void btnClicked(ActionEvent event) throws IOException {
        Parent loginPageParent;
        FXMLLoader loader = new FXMLLoader();
        Stage loginStage = new Stage();
        if (event.getSource().equals(signUp)) {
            loader.setLocation(getClass().getResource("/signUpScene.fxml"));
            loginPageParent = loader.load();
            signUp.disableProperty().bind(loginStage.showingProperty());
        } else {
            loader.setLocation(getClass().getResource("/loginScene.fxml"));
            loginPageParent = loader.load();
            if (loginPageParent != null) {
                LoginSceneController loginCtrl = loader.getController();
                loginCtrl.setMainStage((Stage) ((Node) event.getSource()).getScene().getWindow());
            }
            login.disableProperty().bind(loginStage.showingProperty());
        }
        if (loginPageParent != null) {
            Scene loginPageScene = new Scene(loginPageParent);
            loginStage.setScene(loginPageScene);
            loginStage.setTitle("TU Delft Campus Reservation System");
            loginStage.getIcons().add(new Image("https://simchavos.com/tu.png"));
            loginStage.setResizable(false);
            loginStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred, please try again.");
        }
    }
}
