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

        String roomid = roomID.getText();
        int roomcap = Integer.parseInt(roomCapacity.getText());
        String roomdesc = roomDescription.getText();
        String roomN = roomName.getText();
        String roomPP = roomPicturePath.getText();
        Boolean hasTv = roomTV.isSelected();
        Boolean type = roomEmployee.isSelected();
        Boolean roomWhite = roomWhiteboard.isSelected();
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

        try {
            response = ServerCommunication.createRoom(roomid, roomN, roomcap,
                    roomBuilding, roomdesc, typestr, roomPP, roomWhite, hasTv);
            alert.setContentText(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong, please try again.");
            alert.setTitle("Error");
            alert.showAndWait();
        }

        alert.setTitle("Success");
        alert.showAndWait();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

}
