package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.LoginApp;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.QuoteDisplay;

public class MainSceneController {


    @FXML private Button button;

    /**
     * Handles clicking the button.
     */

    public void buttonClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/loginScene.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Failed to open a new window");
        }


        //LoginApp.main(new String[0]);


        //        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //        alert.setTitle("Quote for you");
        //        alert.setHeaderText(null);
        //        alert.setContentText(ServerCommunication.getQuote());
        //        alert.showAndWait();
    }

}
