package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.regex.Pattern;


public class EditBuildingController {
    @FXML
    private Label buildingName;

    @FXML
    private TextField buildingOpeningHour;

    @FXML
    private TextField buildingClosingHour;

    @FXML
    private TextField buildingPicturesPath;

    @FXML
    private TextField buildingLocation;

    @FXML
    private TextField buildingBikes;

    @FXML
    private Label failtext;

    private static Building building;

    public static void setBuilding(Building building1) {
        building = building1;
    }

    /**Method that displays all information regarding a building.
     *
     */
    @FXML
    public void initialize() throws IOException, URISyntaxException {
        building = (Building) GeneralHomepageController.JsonToEntity(
                ServerCommunication.readBuilding(building.getName()), "Building");
        buildingName.setText(building.getName());
        buildingOpeningHour.setText(building.getOpeningHour().toString());
        buildingClosingHour.setText(building.getClosingHour().toString());
        buildingPicturesPath.setText(building.getPicturesPath());
        buildingLocation.setText(building.getLocation());
        buildingBikes.setText(String.valueOf(building.getBikes()));
    }

    /**Update method for buildings.
     * @param event by which this method is triggered
     */
    public void submitUpdate(ActionEvent event) {
        failtext.setText("");
        String bldName = buildingName.getText();
        String bldoh = buildingOpeningHour.getText();
        String bldch = buildingClosingHour.getText();


        if (bldName.length() == 0) {
            failtext.setText("Please enter a valid name for the new building.");
            return;
        }

        String timeRegex = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pat1 = Pattern.compile(timeRegex);
        Pattern pat2 = Pattern.compile(timeRegex);

        if (!pat1.matcher(bldoh).matches()) {
            failtext.setText("Please input a valid opening time.");
            return;
        }

        if (!pat2.matcher(bldch).matches()) {
            failtext.setText("Please input a valid closing time.");
            return;
        }

        String[] oharr = bldoh.split(":");
        String[] charr = bldch.split(":");
        LocalTime oh;
        oh = LocalTime.of(Integer.parseInt(oharr[0]), Integer.parseInt(oharr[1]));
        LocalTime ch;
        ch = LocalTime.of(Integer.parseInt(charr[0]), Integer.parseInt(charr[1]));

        if (oh.isAfter(ch) || ch.equals(oh)) {
            failtext.setText("Please input a opening time that is before closing time.");
            return;
        }

        String bldpp = buildingPicturesPath.getText();
        String bldloc = buildingLocation.getText();
        if (bldpp.length() == 0) {
            failtext.setText("Please make sure there is a valid picture path.");
            return;
        }

        if (bldloc.length() == 0) {
            failtext.setText("Please make sure the location field is filled in.");
            return;
        }

        String bldBikes = buildingBikes.getText();
        if (bldBikes.length() == 0 || !bldBikes.matches("\\d+")) {
            failtext.setText("Please make sure the number of bikes is correct.");
            return;
        }
        int bldBikesint = Integer.parseInt(bldBikes);


        CloseableHttpResponse response;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int statusCode = 0;

        try {
            response = ServerCommunication
                    .updateBuilding(bldName, bldloc, oh, ch, bldpp, bldBikesint);
            statusCode = response.getStatusLine().getStatusCode();
            alert.setContentText(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            Alert erralert = new Alert(Alert.AlertType.ERROR);
            erralert.setTitle("Error");
            erralert.setContentText("An error occurred, please try again");
            erralert.showAndWait();
        }

        if (statusCode == 201) {
            alert.setTitle("Success");
        } else {
            alert.setTitle("Fail");
        }

        alert.setHeaderText(null);
        alert.showAndWait();

        if (statusCode == 201) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }


}
