package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.entities.Building;


public class EditBuildingController {
    @FXML
    private TextField buildingName;

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

    private static Building building;

    public static void setBuilding(Building building1) {
        building = building1;
    }

    @FXML
    public void initialize() {
        buildingName.setText(building.getName());
        buildingOpeningHour.setText(building.getOpeningHour().toString());
        buildingClosingHour.setText(building.getClosingHour().toString());
        buildingPicturesPath.setText(building.getPicturesPath());
        buildingLocation.setText(building.getLocation());
        buildingBikes.setText(String.valueOf(building.getBikes()));
    }
}
