package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;

import com.sun.javafx.image.impl.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class LoginSceneController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    private Stage mainStage;

    /**
     * Submit the login credentials when clicked on submit.
     * @param event the event that triggers this method
     *
     */
    public void submitClicked(ActionEvent event) throws IOException {
        String usertxt = username.getText();
        String pwtxt = password.getText();
        String authentication = ServerCommunication.sendLoginUser(usertxt, pwtxt);

        //WIP: probably need to add a better form of authorization here.
        if (usertxt.length() == 0 || pwtxt.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all the fields");
            alert.show();
        } else if (authentication.contains("admin")) {
            changeScene(event, usertxt, "/adminHomepageScene.fxml");
        } else if (authentication.contains(usertxt)) {
            changeScene(event, usertxt, "/homepageScene.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Submit clicked");
            alert.setHeaderText(null);
            alert.setContentText(authentication);
            alert.show();
        }
    }

    /**
     * When this method is called, the scene gets changed to the homepage.
     * @param event the event that triggers this method
     *
     */
    public void changeScene(ActionEvent event, String usertxt, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent homePageParent = loader.load();
        Scene homePageScene = new Scene(homePageParent);

        GeneralHomepageController controller = loader.getController();
        controller.setWelcomeMessage(usertxt);

        //Get current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        mainStage.setScene(homePageScene);
        stage.getIcons().add(new Image("https://simchavos.com/tu.png"));
    }

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
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
