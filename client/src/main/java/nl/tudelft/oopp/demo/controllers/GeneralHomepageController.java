package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

import java.io.IOException;

public class GeneralHomepageController {

    private static String username;

    @FXML
    private Label welcomeLabel;

    public void setWelcomeMessage() {
        welcomeLabel.setText("Welcome " + username);
    }

    /**This function makes sure the user is logged out once the logout button is pressed.
     *
     * @param event the event which causes this logout function to start
     */
    public void logout(ActionEvent event) {
        ServerCommunication.resetPubAuth();
        GeneralHomepageController.setUsername("");
        changeScene(event,"/mainScene.fxml");
    }

    /** This method changes the scene to the referenced fxml file.
     *
     * @param event the event that triggers the change of the scene
     * @param path the path to the FXML file
     */

    public void changeScene(ActionEvent event, String path) {
        Parent parent = loadFxml(path);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /** Creates a new stage from the specified fxml file and then
     * optionally disables the node that caused the stage creation.
     *
     * @param path the path to the fxml file that should be loaded into the scene
     * @param node the node which causes the new stage to be made
     */
    public void newStage(String path, Node node) {
        Stage newStage = new Stage();
        Parent parent = loadFxml(path);
        Scene buildings = new Scene(parent);
        newStage.setScene(buildings);
        newStage.show();
        newStage.toFront();
        if (node != null) {
            node.disableProperty().bind(newStage.showingProperty());
        }
    }

    /**
     * Load the provided FXML file.
     * @param path the path to the FXML file
     */
    public Parent loadFxml(String path) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loading failed");
            alert.setContentText("Loading failed");
            alert.setHeaderText(null);
            alert.showAndWait();
            return null;
        }
        return parent;
    }

    public void changeSceneHomepage(ActionEvent event) {
        changeScene(event, "/homepageScene.fxml");
    }

    public void changeSceneAdminHomepage(ActionEvent event) {
        changeScene(event, "/adminHomepageScene.fxml");
    }

    public static void setUsername(String username) {
        GeneralHomepageController.username = username;
    }

    public static String getUsername() {
        return username;
    }
}
