package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        } catch (Exception e) {
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


    /**
     * This methods retrieves the entity from the HTTP response, convert it into JsonObject
     * and then create an Object with the specified type according to that JsonObject.
     * @param response - the Http response returned
     * @param type - the type of the object
     * @return - the object created
     * @throws IOException thrown when something goes wrong with IO
     */
    public static Object jsonToEntity(CloseableHttpResponse response, String type) throws IOException {
        String jsonString  = EntityUtils.toString(response.getEntity());
        JSONObject objectJson = new JSONObject(jsonString);
        if (type.equals("Room")) {
            return new Room(objectJson);
        }
        if (type.equals("Building")) {
            return new Building(objectJson);
        }
        return null;
    }

    /**
     * This method retrieves the array of Rooms from the HTTP response, convert it into JsonArray
     * and then create an ObservableList of Rooms.
     * @param response - the Http response returned
     * @return - the ObservableList of rooms
     * @throws IOException thrown when something goes wrong with IO
     */
    public static ObservableList<Room> jsonArrayToRoom(CloseableHttpResponse response) throws IOException {
        String jsonArray = EntityUtils.toString(response.getEntity());
        JSONArray roomJsonArray = new JSONArray(jsonArray);
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for (int i = 0; i < roomJsonArray.length(); i++) {
            rooms.add(new Room(roomJsonArray.getJSONObject(i)));
        }
        return rooms;
    }

    /**
    * This method retrieves the array of Rooms from the HTTP response, convert it into JsonArray
    * and then create an ObservableList of Rooms.
    * @param response - the Http response returned
    * @return - the ObservableList of Room String
    * @throws IOException thrown when something goes wrong with IO
    */
    public static ObservableList<String> jsonArrayToRoomS(CloseableHttpResponse response) throws IOException {
        String jsonArray = EntityUtils.toString(response.getEntity());
        JSONArray roomJsonArray = new JSONArray(jsonArray);
        ObservableList<String> rooms = FXCollections.observableArrayList();
        for (int i = 0; i < roomJsonArray.length(); i++) {
            rooms.add(new Room(roomJsonArray.getJSONObject(i)).toString());
        }
        return rooms;
    }

    /**
     * This method retrieves the array of Buildings from the HTTP response, convert it into JsonArray
     * and then create an ObservableList of Building.
     * @param response - the Http response returned
     * @return - the ObservableList of Building
     * @throws IOException thrown when something goes wrong with IO
     */
    public static ObservableList<Building> jsonArrayToBuilding(CloseableHttpResponse response) throws IOException {
        String jsonArray = EntityUtils.toString(response.getEntity());
        JSONArray buildingJsonArray = new JSONArray(jsonArray);
        ObservableList<Building> buildings = FXCollections.observableArrayList();
        for (int i = 0; i < buildingJsonArray.length(); i++) {
            buildings.add(new Building(buildingJsonArray.getJSONObject(i)));
        }
        return buildings;
    }

    /**
     * This method retrieves the array of RoomReservations from the HTTP response, convert it into JsonArray
     * and then create an ObservableList of RoomReservation.
     * @param response - the Http response returned
     * @return - the ObservableList of RoomReservation
     * @throws IOException thrown when something goes wrong with IO
     */
    public static ObservableList<RoomReservation> jsonArrayToRoomReservation(CloseableHttpResponse response)
            throws IOException {
        String jsonArray = EntityUtils.toString(response.getEntity());
        JSONArray roomReservationJsonArray = new JSONArray(jsonArray);
        ObservableList<RoomReservation> roomReservations = FXCollections.observableArrayList();
        for (int i = 0; i < roomReservationJsonArray.length(); i++) {
            roomReservations.add(new RoomReservation(roomReservationJsonArray.getJSONObject(i)));
        }
        return roomReservations;
    }

    /**
     * This method parses a String into LocalDateTime.
     * @param dateTime - the String to be parsed
     * @return - A LocalDateTime object created according to the String
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        List<Integer> date = Arrays.stream(dateTime.split("T")[0].split("-"))
                .map((i) -> Integer.parseInt(i))
                .collect(Collectors.toList());
        List<Integer> time = Arrays.stream(dateTime.split("T")[1].split(":"))
                .map((i) -> Integer.parseInt(i))
                .collect(Collectors.toList());
        return LocalDateTime.of(date.get(0), Month.of(date.get(1)), date.get(2),
                time.get(0), time.get(1), time.get(2));
    }

    /**
     * General error alert to be used in all controllers.
     */
    public static void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Something went wrong, please try again.");
        alert.showAndWait();
    }

}
