package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RoomReservation")
public class RoomReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "beginTime")
    private Timestamp beginTime;

    @Column(name = "numberOfSlots")
    private int numberOfSlots;

    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;

    public RoomReservation() {
    }

    public RoomReservation(User user, Timestamp beginTime, int numberOfSlots, Room room){
        this.user = user;
        this.beginTime = beginTime;
        this.numberOfSlots = numberOfSlots;
        this.room = room;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomReservation that = (RoomReservation) o;
        return that.getId().equals(Id);
    }


}
