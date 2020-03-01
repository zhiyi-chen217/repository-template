package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "openingHour")
    private LocalTime openingHour;

    @Column(name = "closingHour")
    private LocalTime closingHour;

    @Column(name = "picturesPath")
    private String picturesPath;

    @Column(name = "bikes")
    private int bikes;

    @Column(name = "rooms")
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Room> rooms;


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
    public Building(String name, String location, LocalTime openingHour,
                    LocalTime closingHour, int bikes) {
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
