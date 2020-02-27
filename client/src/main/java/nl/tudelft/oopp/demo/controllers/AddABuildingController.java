package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class AddABuildingController {
    @FXML
    private TextField buildingName;

    @FXML
    private TextField buildingBikes;

    @FXML
    private TextField buildingOpeningHour;

    @FXML
    private TextField buildingClosingHour;

    @FXML
    private TextField buildingPicturesPath;

    @FXML
    private TextField buildingLocation;

    @FXML
    private Label failtext;

    /**
     * Add the building on the click of the add button.
     * @param event the event that called this function
     */
    public void addBuilding(ActionEvent event) {
        String bldName = buildingName.getText();
        String bldoh = buildingOpeningHour.getText();
        String bldch = buildingClosingHour.getText();


        if (bldName.length() == 0) {
            failtext.setText("Please enter a valid name for the new building.");
            return;
        }

        String timeRegex = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pat = Pattern.compile(timeRegex);

        if (!pat.matcher(bldoh).matches()) {
            failtext.setText("Please input a valid opening time.");
            return;
        }

        if (!pat.matcher(bldch).matches()) {
            failtext.setText("Please input a valid closing time");
        }

        String[] oharr = bldoh.split(":");
        String[] charr = bldch.split(":");
        LocalTime oh;
        oh = LocalTime.of(Integer.parseInt(oharr[1]), Integer.parseInt(oharr[2]));
        LocalTime ch;
        ch = LocalTime.of(Integer.parseInt(charr[1]), Integer.parseInt(charr[2]));

        String bldpp = buildingPicturesPath.getText();
        String bldloc = buildingLocation.getText();
        if (bldpp.length() == 0) {
            failtext.setText("Please make sure there is a valid picture path");
            return;
        }

        if (bldloc.length() == 0) {
            failtext.setText("Please make sure the location field is filled in.");
            return;
        }

        int bldBikes = Integer.parseInt(buildingBikes.getText());
        if (bldBikes < 0) {
            failtext.setText("Please make sure there is not a negative number of bikes.");
            return;
        }

        CloseableHttpResponse response;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int statusCode = 200;

        try {
            response = ServerCommunication
                    .createBuilding(bldName, bldloc, oh, ch, bldpp, bldBikes);
            statusCode = response.getStatusLine().getStatusCode();
            alert.setContentText(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            Alert erralert = new Alert(Alert.AlertType.ERROR);
            erralert.setTitle("Error");
            erralert.setContentText("An error occurred, please try again");
            erralert.showAndWait();
        }

        if (statusCode == 202) {
            alert.setTitle("Success");
        } else {
            alert.setTitle("Unsuccessful");
        }

        alert.setHeaderText(null);
        alert.showAndWait();

        if (statusCode == 202) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }


    }

}
