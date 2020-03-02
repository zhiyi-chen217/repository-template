package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.*;


@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ofBuilding")
    private Building ofBuilding;



    public Restaurant() {
    }

    public Restaurant(String name, Building ofBuilding) {
        this.name = name;
        this.ofBuilding = ofBuilding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building getOfBuilding() {
        return ofBuilding;
    }

    public void setOfBuilding(Building ofBuilding) {
        this.ofBuilding = ofBuilding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return Objects.equals(name, that.name);
    }

}
