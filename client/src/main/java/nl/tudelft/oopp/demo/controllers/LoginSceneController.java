package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
     * @param event the event that triggers this method
     *
     */
    public void submitClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Submit clicked");
        alert.setHeaderText(null);

        String usertxt = username.getText();
        String pwtxt = password.getText();
        String authentication = ServerCommunication.sendLoginUser(usertxt, pwtxt);

        alert.setContentText(authentication);
        alert.show();

        //WIP: probably need to add a better form of authorization here.
        if (authentication.contains(usertxt)) {
            changeScene(event);
        }
    }

    /**
     * When this method is called, the scene gets changed to the homepage.
     * @param event the event that triggers this method
     *
     */
    public void changeScene(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("/homepageScene.fxml"));
        Scene homePageScene = new Scene(homePageParent);

        //Get current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homePageScene);
        stage.show();
    }

    /**
     * Submit the login credentials of an admin when clicked on submit.
     * Note: this functionality will be removed soon (switching to single admin account).
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
