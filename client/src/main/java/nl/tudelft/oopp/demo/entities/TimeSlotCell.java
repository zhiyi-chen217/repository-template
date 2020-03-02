package nl.tudelft.oopp.demo.entities;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Paint;

import java.time.LocalTime;

public class TimeSlotCell extends ListCell<String> {

    public static ObservableList<RoomReservation> roomReservations;

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            return;
        }
        LocalTime startTime = LocalTime.parse(item.split("--")[0]);
        setDisable(false);
        for (RoomReservation roomReservation: roomReservations) {
            LocalTime beginTime = roomReservation.getBeginTime().toLocalTime();
            LocalTime endTime = roomReservation.getEndTime().toLocalTime();
            if ((startTime.isAfter(beginTime) || startTime.equals(beginTime)) && startTime.isBefore(endTime)) {
                this.setDisable(true);
                this.setTextFill(Paint.valueOf("grey"));
            }
        }
        setText(item);
    }
}
