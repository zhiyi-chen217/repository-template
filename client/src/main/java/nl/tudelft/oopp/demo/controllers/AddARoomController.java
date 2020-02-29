package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;


public class AddARoomController {
    @FXML
    private TextField roomID;

    @FXML
    private TextField roomCapacity;

    @FXML
    private TextArea roomDescription;

    @FXML
    private TextField roomName;

    @FXML
    private TextField roomPicturePath;

    @FXML
    private CheckBox roomTV;

    @FXML
    private CheckBox roomEmployee;

    @FXML
    private CheckBox roomWhiteboard;

    @FXML
    private Label buildingName;

    private static Building building;

    @FXML
    private Label failtext;

    public void initialize() {
        buildingName.setText(building.getName());
    }

    public static void setBuilding(Building building1) {
        building = building1;
    }

    /**
     * Creates a new room entity and then sends it to the server.
     */
    public void addRoom(ActionEvent event) {
        failtext.setText("");

        String roomid = roomID.getText();
        if (roomid.length() == 0) {
            failtext.setText("Please input a valid room id.");
            return;
        }

        String roomcapstr = roomCapacity.getText();
        if (!roomcapstr.matches("\\d+")) {
            failtext.setText("Please input a valid capacity");
            return;
        }
        int roomcap = Integer.parseInt(roomCapacity.getText());

        String roomdesc = roomDescription.getText();
        if (roomdesc.length() == 0) {
            failtext.setText("Please input a valid description");
            return;
        }

        String roomN = roomName.getText();
        if (roomN.length() == 0) {
           failtext.setText("Please input a valid name.");
           return;
        }

        String roomPP = roomPicturePath.getText();
        if (roomPP.length() == 0) {
            failtext.setText("Please input a valid picture path");
            return;
        }

        boolean hasTv = roomTV.isSelected();
        boolean type = roomEmployee.isSelected();
        boolean roomWhite = roomWhiteboard.isSelected();
        String roomBuilding = building.getName();

        String typestr;
        if (type) {
            typestr = "Employee";
        } else {
            typestr = "ALL_CAN_USE";
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        CloseableHttpResponse response;
        int statusCode = 0;

        try {
            response = ServerCommunication.createRoom(roomid, roomN, roomcap,
                    roomBuilding, roomdesc, typestr, roomPP, roomWhite, hasTv);
            alert.setContentText(EntityUtils.toString(response.getEntity()));
            statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong, please try again.");
            alert.setTitle("Error");
            alert.showAndWait();
            return;
        }

        if (statusCode == 201) {
            alert.setTitle("Success");
        } else {
            alert.setTitle("Unsuccessful");
        }

        alert.showAndWait();

        if (statusCode == 201) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
