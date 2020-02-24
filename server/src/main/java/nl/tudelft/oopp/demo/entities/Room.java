package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;


@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "roomId")
    private String roomId;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @ManyToOne
    @JoinColumn
    private Building building;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "picturesPath")
    private String picturesPath;

    @Column(name = "whiteboard")
    private boolean whiteboard;

    @Column(name = "tv")
    private boolean tv;

    public Room() {
    }

    /**Constructor of a room.
     * @param roomId is the unique id of a room
     * @param name is the name of the room
     * @param capacity is the capacity of the room
     * @param whiteboard is whether the room contains a whiteboard
     * @param tv is whether the room contains the tv
     * @param building is the building the room is located in
     */
    public Room(String roomId, String name, int capacity, boolean whiteboard,
                            boolean tv, Building building) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.description = "";
        this.type = "AllCanUse";
        this.picturesPath = "";
        this.whiteboard = whiteboard;
        this.tv = tv;
        this.building = building;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicturesPath() {
        return picturesPath;
    }

    public void setPicturesPath(String picturesPath) {
        this.picturesPath = picturesPath;
    }

    public boolean isWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(boolean whiteboard) {
        this.whiteboard = whiteboard;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
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
        Room room = (Room) o;
        return roomId.equals(room.roomId);

    }

}
