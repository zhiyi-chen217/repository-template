package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.util.Objects;

@Entity
@Table(name = "FoodOrder")
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String Id;

    @JoinColumn
    private Restaurant restaurant;

    @JoinColumn
    private User user;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "deliverMethod")
    private String deliverMethod;

    @Column(name = "location")
    private String location;

    public FoodOrder() {
    }

    public FoodOrder(Restaurant restaurant, User user, double totalPrice, String deliverMethod, String location){
        this.restaurant = restaurant;
        this.user = user;
        this.totalPrice = totalPrice;
        this.deliverMethod = deliverMethod;
        this.location = location;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliverMethod() {
        return deliverMethod;
    }

    public void setDeliverMethod(String deliverMethod) {
        this.deliverMethod = deliverMethod;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrder foodOrder = (FoodOrder) o;
        return foodOrder.getId().equals(Id);
    }


}
