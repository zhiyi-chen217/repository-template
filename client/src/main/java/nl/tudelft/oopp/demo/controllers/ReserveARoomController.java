package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ReserveARoomController extends ReserveBikeController {
    @FXML
    ListView<String> roomListView;

    @FXML
    Button continueButton;

    public void initialize() {
        roomListView.setVisible(false);
        continueButton.setVisible(false);
    }
}
