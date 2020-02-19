package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.LoginApp;
import nl.tudelft.oopp.demo.views.QuoteDisplay;


public class MainSceneController {


    @FXML private TextField textfield1;
    @FXML private TextField textfield2;
    /**
     * Handles clicking the button.
     */

    public void buttonClicked() {
        LoginApp.main(new String[0]);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Quote for you");
//        alert.setHeaderText(null);
//        alert.setContentText(ServerCommunication.getQuote());
//        alert.showAndWait();
    }

}
