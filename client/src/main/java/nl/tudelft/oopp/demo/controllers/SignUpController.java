package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

import java.io.IOException;


public class SignUpController {
    @FXML
    private TextField netid;
    @FXML
    private TextField email1;
    @FXML
    private TextField email2;
    @FXML
    private PasswordField pass1;
    @FXML
    private PasswordField pass2;
    @FXML
    private Label failtext;

    /**
     * Send the sign up details to the server.
     */
    public void signUp(ActionEvent event) {
        failtext.setText("");
        String netidstr = netid.getText();

        if (netidstr.length() == 0) {
            failtext.setText("Please insert a valid netid.");
            return;
        }

        String emailstr1 = email1.getText();
        String emailstr2 = email2.getText();

        if (emailstr1.length() == 0) {
            failtext.setText("Please insert a valid email address.");
            return;
        }
        if (!emailstr1.equals(emailstr2)) {
            failtext.setText("Please make sure the two email addresses are the same..");
            return;
        }

        String passstr1 = pass1.getText();
        String passstr2 = pass2.getText();

        if (passstr1.length() == 0) {
            failtext.setText("Please insert a valid password.");
            return;
        }
        if (!passstr1.equals(passstr2)) {
            failtext.setText("Please make sure the two passwords match.");
            return;
        }

        ServerCommunication.sendSignUp(netidstr, emailstr1, passstr1);
        
        try {
            Parent signupPageParent = FXMLLoader.load(getClass().getResource("/loginScene.fxml"));
            Scene signupPageScene = new Scene(signupPageParent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(signupPageScene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred, please try again.");
        }


    }
}
