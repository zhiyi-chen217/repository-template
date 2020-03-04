package nl.tudelft.oopp.demo.entities;

import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Objects;

public class Building {
    private String name;

    private String location;

    private LocalTime openingHour;

    private LocalTime closingHour;

    private String picturesPath;

    private int bikes;

    /**Constructor of the Building class.
     * @param building is the inputted building information
     */
    public Building(JSONObject building) {
        this.name = (String) building.get("name");
        this.location = (String) building.get("location");
        String[] temp1 =  ((String)building.get("openingHour")).split(":");
        LocalTime openingHour = LocalTime.of(Integer.parseInt(temp1[0]),
                Integer.parseInt(temp1[1]), Integer.parseInt(temp1[2]));
        this.openingHour = openingHour;
        String[] temp2 =  ((String)building.get("closingHour")).split(":");
        LocalTime closingHour = LocalTime.of(Integer.parseInt(temp2[0]),
                Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]));
        this.closingHour = closingHour;
        this.bikes = (Integer) building.get("bikes");
        this.picturesPath = building.getString("picturesPath");
    }

    /**Constructor for the building class.
     *
     * @param name The name of the building
     * @param location the location of the building
     * @param openingHour the hour at which the building opens
     * @param closingHour the hour at which the building closes
     * @param bikes the amount of bikes at the building
     */
    public Building(String name, String location, LocalTime openingHour,
                    LocalTime closingHour, int bikes, String picturesPath) {
        this.name = name;
        this.location = location;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.bikes = bikes;
        this.picturesPath = picturesPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public LocalTime getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(LocalTime openingHour) {
        this.openingHour = openingHour;
    }

    public LocalTime getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(LocalTime closingHour) {
        this.closingHour = closingHour;
    }

    public String getPicturesPath() {
        return picturesPath;
    }

    public void setPicturesPath(String picturesPath) {
        this.picturesPath = picturesPath;
    }

    public int getBikes() {
        return bikes;
    }

    public void setBikes(int bikes) {
        this.bikes = bikes;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        return bikes == building.bikes
                && name.equals(building.name)
                && location.equals(building.location)
                && openingHour.equals(building.openingHour)
                && closingHour.equals(building.closingHour)
                && Objects.equals(picturesPath, building.picturesPath);
    }
}
