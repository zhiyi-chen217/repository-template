package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "RoomReservation")
public class RoomReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user")
    @NotNull
    private User user;

    @Column(name = "beginTime")
    private LocalDateTime beginTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "room")
    @NotNull
    private Room room;

    public RoomReservation() {
    }

    /**Constructor of a room reservation.
     * @param user is the user who has placed a reservation
     * @param beginTime is the time the reservation starts
     * @param endTime is the amount of consecutive slots of a certain fixed length
     * @param room is the room which is reserved
     */
    public RoomReservation(User user, LocalDateTime beginTime, LocalDateTime endTime, Room room) {
        this.user = user;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return that.getId().equals(id);
    }


}
