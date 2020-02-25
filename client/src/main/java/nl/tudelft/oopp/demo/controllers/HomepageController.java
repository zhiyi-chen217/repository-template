package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

import java.awt.*;
import java.io.IOException;

public class HomepageController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutBtn;

    public void setWelcomeMessage(String message) {
        welcomeLabel.setText("Welcome " + message);
    }

    /**This function makes sure the user is logged out once the logout button is pressed.
     *
     * @param event the event which causes this logout function to start
     * @throws IOException the exception can be thrown if the fxml loader cant load the resource
     */
    public void logout(ActionEvent event) throws IOException {
        ServerCommunication.resetPubAuth();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainScene.fxml"));
        Parent homePageParent = loader.load();
        Scene homePageScene = new Scene(homePageParent);

        //Get current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homePageScene);
        stage.getIcons().add(new Image("https://simchavos.com/tu.png"));
        stage.show();
    }
}

