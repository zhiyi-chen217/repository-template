package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "bikeReservation")
public class BikeReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rentBuilding")
    private Building rentBuilding;

    @ManyToOne
    @JoinColumn(name = "returnBuilding")
    private Building returnBuilding;

    @Column(name = "beginTime")
    private Timestamp beginTime;

    @Column(name = "numberOfSlots")
    private int numberOfSlots;

    public BikeReservation() {
    }

    public BikeReservation(User user, Building rentBuilding, Building returnBuilding, Timestamp beginTime, int numberOfSlots){
        this.user = user;
        this.returnBuilding = returnBuilding;
        this.rentBuilding = rentBuilding;
        this.beginTime = beginTime;
        this.numberOfSlots = numberOfSlots;

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Building getRentBuilding() {
        return rentBuilding;
    }

    public void setRentBuilding(Building rentBuilding) {
        this.rentBuilding = rentBuilding;
    }

    public Building getReturnBuilding() {
        return returnBuilding;
    }

    public void setReturnBuilding(Building returnBuilding) {
        this.returnBuilding = returnBuilding;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeReservation that = (BikeReservation) o;
        return that.getId().equals(Id);
    }

}
