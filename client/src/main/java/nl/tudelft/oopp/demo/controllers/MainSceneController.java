package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainSceneController {

    /**
     * Handles clicking the button.
     */

    public void buttonClicked(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);

        //Get current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

}
