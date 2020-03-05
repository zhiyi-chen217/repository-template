package nl.tudelft.oopp.demo.controllers;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class ReserveARoomController extends ReserveBikeController {

    @FXML TableView<Room> roomTableView;
    @FXML TableColumn<Room, String> roomColumn;
    @FXML TableColumn<Room, String> whiteboardColumn;
    @FXML TableColumn<Room, String> tvColumn;
    @FXML TableColumn<Room, Integer> capacityColumn;
    @FXML Button continueButton;

    @FXML ChoiceBox<Building> buildingChoiceBox;

    @FXML CheckBox whiteboardCheckBox;
    @FXML CheckBox tvCheckBox;
    @FXML TextField capacityTextField;
    @FXML DatePicker datePicker;
    @FXML TextField searchTextField;

    public static ObservableList<Building> buildings;

    public static ObservableList<Room> rooms;

    /**
     * This initialize method contains the set up procedures for the ReserveARoom page.
     * @throws IOException thrown when something goes wrong with IO
     * @throws URISyntaxException thrown when the URI is falsely constructed
     */
    public void initialize() throws IOException, URISyntaxException {
        buildings = GeneralHomepageController
                .jsonArrayToBuilding(ServerCommunication.readBuilding(null));
        buildingChoiceBox.setItems(buildings);
        buildingChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldBuilding, newBuilding) -> {
                    try {
                        changeSelectedEvent(v, oldBuilding, newBuilding);
                        roomTableView.setVisible(true);
                        continueButton.setVisible(false);
                        datePicker.setVisible(true);
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                });

        roomColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(
                cellData.getValue().getRoomId() + " -- " + cellData.getValue().getName()));
        whiteboardColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().isWhiteboard()) {
                return new ReadOnlyObjectWrapper<>("Yes");
            } else {
                return new ReadOnlyObjectWrapper<>("No");
            }
        });
        tvColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().isTv()) {
                return new ReadOnlyObjectWrapper<>("Yes");
            } else {
                return new ReadOnlyObjectWrapper<>("No");
            }
        });
        capacityColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("capacity"));

        roomTableView.setVisible(false);
        roomTableView.getSelectionModel().selectedItemProperty().addListener((v, oldRoom, newRoom) -> {
            if (newRoom != null) {
                continueButton.setVisible(true);
            }
        });
        continueButton.setVisible(false);
        datePicker.setVisible(false);
    }

    /**
     * This methods specifies what should be done when the selection of the List views are changed.
     * @param v - the observable item
     * @param oldBuilding - the previous selected building
     * @param newBuilding - the latest selected building
     * @throws IOException thrown when something goes wrong with IO
     * @throws URISyntaxException thrown when the URI is falsely constructed
     */
    public void changeSelectedEvent(Observable v, Building oldBuilding, Building newBuilding)
            throws IOException, URISyntaxException {
        if (newBuilding == null) {
            return;
        }
        rooms = GeneralHomepageController
                .jsonArrayToRoom(ServerCommunication.readRoom(null, newBuilding.getName()));
        roomTableView.setItems(rooms);
    }

    /**
     * This method gets called when the search button gets pressed.
     * It sends the values of the nodes of this page to ServerCommunication
     * to start the filtering process.
     * @throws IOException thrown when something goes wrong with IO
     * @throws URISyntaxException thrown when the URI is falsely constructed
     */
    public void filter() throws IOException, URISyntaxException {
        Building building = buildingChoiceBox.getSelectionModel().getSelectedItem();
        if (building == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a building");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        String buildingName = building.getName();
        boolean tv = tvCheckBox.isSelected();
        boolean whiteboard = whiteboardCheckBox.isSelected();
        int capacity;
        if (!capacityTextField.getText().equals("")) {
            capacity = Integer.parseInt(capacityTextField.getText());
        } else {
            capacity = 0;
        }
        rooms = GeneralHomepageController.jsonArrayToRoom(ServerCommunication.filterRooms(buildingName,
                tv, whiteboard, capacity));
        roomTableView.setItems(rooms);
        roomTableView.setVisible(true);
        datePicker.setVisible(true);
        continueButton.setVisible(false);
    }

    /** Sends the room name to the server for searching.
     *
     * @throws IOException thrown when something goes wrong with IO
     * @throws URISyntaxException thrown when the URI is falsely constructed
     */
    public void search() throws IOException, URISyntaxException {
        String roomName = searchTextField.getText();
        Building building = buildingChoiceBox.getSelectionModel().getSelectedItem();
        if (building == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a building");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        String buildingName = building.getName();
        if (roomName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in the field");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        rooms = GeneralHomepageController.jsonArrayToRoom(ServerCommunication.readRoomByName(roomName, buildingName));
        roomTableView.setItems(rooms);
        roomTableView.setVisible(true);
        datePicker.setVisible(true);
        continueButton.setVisible(false);
    }

    /**
     * This method sets up the RoomInfo page, and then change to that page.
     * @param event - the event generated
     */
    public void stageRoomInfo(ActionEvent event) {
        RoomInfoController
                .setRoom(roomTableView.getSelectionModel().getSelectedItem());
        changeScene(event, "/roomInfoScene.fxml");
    }
}
