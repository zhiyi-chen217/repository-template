package nl.tudelft.oopp.demo.entities;

import nl.tudelft.oopp.demo.controllers.GeneralHomepageController;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class RoomReservation {

    private Long id;

    private String user;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Room room;

    private Building building;

    public RoomReservation() {
    }

    /**Constructor of a room reservation.
     * @param user is the user who has placed a reservation
     * @param beginTime is the time the reservation starts
     * @param endTime is the amount of consecutive slots of a certain fixed length
     * @param room is the room which is reserved
     */
    public RoomReservation(String user, LocalDateTime beginTime, LocalDateTime endTime, Room room) {
        this.user = user;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.room = room;
        this.building = room.getBuilding();
    }



    public RoomReservation(JSONObject jsonObject) {
        this.id = jsonObject.getLong("id");
        this.user = jsonObject.getJSONObject("user").getString("user_id");
        this.beginTime = GeneralHomepageController
                .StringToLocalDateTime(jsonObject.getString("beginTime"));
        this.endTime = GeneralHomepageController
                .StringToLocalDateTime(jsonObject.getString("endTime"));
        this.room = new Room(jsonObject.getJSONObject("room"));
        this.building = new Building(jsonObject.getJSONObject("room").getJSONObject("building"));
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

    public LocalDateTime getEndTime() { return endTime; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Room getRoom() {return this.room;}

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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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
