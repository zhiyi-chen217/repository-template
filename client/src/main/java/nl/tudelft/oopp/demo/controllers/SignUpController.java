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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


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
    public void signUp(ActionEvent event) throws IOException, URISyntaxException {
        /*CloseableHttpResponse re = ServerCommunication.updateBuilding("bb", "loc",
                LocalTime.of(12,00), LocalTime.of(19, 00),
                "", 3);

         */
        CloseableHttpResponse re = ServerCommunication.readRoom("ewi01", null);
        System.out.println(new Room(new JSONObject(EntityUtils.toString(re.getEntity()))).toString());
//        CloseableHttpResponse re = ServerCommunication.updateRoom("ewi02", "01", 9,
//                "b1", "", "ALL_CAN_USE", "", true, true);
//        System.out.println(re.getStatusLine().getStatusCode());
        //CloseableHttpResponse re = ServerCommunication.deleteRoom(List.of("ewi01"));
        //System.out.println(EntityUtils.toString(re.getEntity()));
        failtext.setText("");
        String netidstr = netid.getText();

        if (netidstr.length() == 0 || !netidstr.matches("[a-zA-Z0-9]+")) {
            failtext.setText("Please insert a valid NetID.");
            return;
        }

        String emailstr1 = email1.getText();
        String emailstr2 = email2.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if (emailstr1.length() == 0 || !pat.matcher(emailstr1).matches()) {
            failtext.setText("Please enter a valid email address.");
            return;
        }
        if (!emailstr1.equals(emailstr2)) {
            failtext.setText("Please make sure the two email addresses are the same.");
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
        if (passstr1.length() < 8) {
            failtext.setText("Password needs to be 8 characters or longer.");
            return;
        }

        CloseableHttpResponse response = ServerCommunication
                .sendSignUp(netidstr, emailstr1, passstr1);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (response.getStatusLine().getStatusCode() == 202) {
            alert.setTitle("Signup successful");
        } else {
            alert.setTitle("Signup unsuccessful");
        }
        alert.setContentText(EntityUtils.toString(response.getEntity(), "UTF-8"));
        alert.setHeaderText(null);
        alert.showAndWait();
        if (response.getStatusLine().getStatusCode() == 202) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
