package nl.tudelft.oopp.demo.controllers;

import com.sun.javafx.image.impl.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.views.MainDisplay;

import javax.swing.*;
import java.io.IOException;

public class GeneralHomepageController {

    private static String username;

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutBtn;

    public void setWelcomeMessage() {
        welcomeLabel.setText("Welcome " + username);
    }

    /**This function makes sure the user is logged out once the logout button is pressed.
     *
     * @param event the event which causes this logout function to start
     * @throws IOException the exception can be thrown if the fxml loader cant load the resource
     */
    public void logout(ActionEvent event) throws IOException {
        ServerCommunication.resetPubAuth();
        GeneralHomepageController.setUsername("");
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

    /** This method changes the scene to the referenced fxml file.
     *
     * @param event the event that triggers the change of the scene
     * @param path the path to the FXML file
     */

    public void changeScene(ActionEvent event, String path) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Creates a new stage from the specified fxml file.
     * @param path the path to the fxml file that should be loaded into the scene
     */
    public void newStage(String path) throws IOException {
        Parent buildingRoomParent = FXMLLoader.load(getClass().getResource(path));
        Scene buildings = new Scene(buildingRoomParent);
        Stage stage = new Stage();
        stage.setScene(buildings);
        stage.show();
    }

    public void changeSceneHomepage(ActionEvent event) throws IOException {
        changeScene(event, "/homepageScene.fxml");
    }

    public void changeSceneAdminHomepage(ActionEvent event) throws IOException {
        changeScene(event, "/adminHomepageScene.fxml");
    }

    public static void setUsername(String username) {
        GeneralHomepageController.username = username;
    }

    public static String getUsername() {
        return username;
    }
}
