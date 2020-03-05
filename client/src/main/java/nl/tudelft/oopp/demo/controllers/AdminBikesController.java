package nl.tudelft.oopp.demo.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.RoomServerCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.communication.BuildingServerCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminBikesController extends GeneralHomepageController {

    @FXML private Button editBikeButton;
    @FXML private Button addBikeButton;
    @FXML private Button deleteBikeButton;
    @FXML private ListView<String> bikeListView;
    @FXML private ChoiceBox<Building> buildingChoiceBox;
    @FXML private Button refreshButton;

    /**
     * This method sets up the AddBike page and change to that page.
     */
    public void stageAddBike() {
        newStage("/addABikeScene.fxml", addBikeButton);
    }

    /**
     * This method sets up the EditBike page and change to that page.
     */
    public void stageEditBike() {
        newStage("/editBikeScene.fxml", editBikeButton);
    }

}