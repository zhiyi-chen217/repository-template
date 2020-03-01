package nl.tudelft.oopp.demo.entities;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TimeSlotCellFactory implements Callback<ListView<String>, ListCell<String>>
{
    @Override
    public ListCell<String> call(ListView<String> listview)
    {
        return new TimeSlotCell();
    }
}