package nl.tudelft.oopp.demo.entities;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "food")
public class Food {

    @Id
    @Column(name = "food")
    @NotNull
    private String name;


    @Column(name = "price")
    private double price;

    public Food() {
    }

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return name.equals(food.name);
    }


}
