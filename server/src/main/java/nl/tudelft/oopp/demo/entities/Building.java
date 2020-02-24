package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "openingHour")
    private double openingHour;

    @Column(name = "closingHour")
    private double closingHour;

    @Column(name = "picturesPath")
    private String picturesPath;

    @Column(name = "bikes")
    private int bikes;



    public Building() {
    }

    /**Constructor for the building class.
     *
     * @param name The name of the building
     * @param location the location of the building
     * @param openingHour the hour at which the building opens
     * @param closingHour the hour at which the building closes
     * @param bikes the amount of bikes at the building
     */
    public Building(String name, String location, double openingHour,
                    double closingHour, int bikes) {
        this.name = name;
        this.location = location;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.bikes = bikes;
        this.picturesPath = "";
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


    public double getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(double openingHour) {
        this.openingHour = openingHour;
    }

    public double getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(double closingHour) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        return this.name.equals(building.name);
    }


}
