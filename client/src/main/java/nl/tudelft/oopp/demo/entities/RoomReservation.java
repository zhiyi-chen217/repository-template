package nl.tudelft.oopp.demo.entities;

import nl.tudelft.oopp.demo.controllers.GeneralHomepageController;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Objects;

public class RoomReservation {

    private Long id;

    private String user;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Room room;

    public RoomReservation() {
    }

    /**Constructor of a room reservation.
     * @param user is the user who has placed a reservation
     * @param beginTime is the time the reservation starts
     * @param endTime is the amount of consecutive slots of a certain fixed length
     * @param room is the room which is reserved
     */
    public RoomReservation(String user, LocalDateTime beginTime, LocalDateTime endTime, Room room, Long id) {
        this.user = user;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.room = room;
        this.id = id;
    }

    /**
     * This constructor constructs a room reservation object according to the provided JsonObject.
     * @param jsonObject - the json object providing all the information
     */
    public RoomReservation(JSONObject jsonObject) {
        this.id = jsonObject.getLong("id");
        this.user = jsonObject.getJSONObject("user").getString("user_id");
        this.beginTime = GeneralHomepageController
                .stringToLocalDateTime(jsonObject.getString("beginTime"));
        this.endTime = GeneralHomepageController
                .stringToLocalDateTime(jsonObject.getString("endTime"));
        this.room = new Room(jsonObject.getJSONObject("room"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getBeginTimeString() {
        return this.getBeginTime().toString().split("T")[0] + "\n"
                + this.getBeginTime().toString().split("T")[1];
    }

    public String getEndTimeString() {
        return this.getEndTime().toString().split("T")[0] + "\n"
                + this.getEndTime().toString().split("T")[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomReservation)) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && beginTime.equals(that.beginTime)
                && endTime.equals(that.endTime)
                && room.equals(that.room);
    }
}
