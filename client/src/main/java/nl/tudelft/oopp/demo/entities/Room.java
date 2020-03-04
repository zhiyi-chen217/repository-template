package nl.tudelft.oopp.demo.entities;

import org.json.JSONObject;

import java.util.Objects;

public class  Room {

    private String roomId;

    private String name;

    private int capacity;

    private Building building;

    private String description;

    private String type;

    private String picturesPath;

    private boolean whiteboard;

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
                boolean tv, Building building, String description, String picturesPath) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.type = "AllCanUse";
        this.picturesPath = picturesPath;
        this.whiteboard = whiteboard;
        this.tv = tv;
        this.building = building;
    }

    /**Constructor of the room class.
     * @param room is the inputted room information
     */
    public Room(JSONObject room) {
        this.roomId = room.getString("roomId");
        this.name = room.getString("name");
        this.capacity = room.getInt("capacity");
        this.description = room.getString("description");
        this.type = room.getString("type");
        this.picturesPath = room.getString("picturesPath");
        this.whiteboard = room.getBoolean("whiteboard");
        this.tv = room.getBoolean("tv");
        this.building = new Building(room.getJSONObject("building"));
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

    /**
     * Sets the type of room according to a boolean value.
     * @param type true if type should be employee only, false otherwise
     */
    public void setType(Boolean type) {
        if (type) {
            this.type = "Employee";
        } else {
            this.type = "AllCanUse";
        }
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

    public String getBuildingName() {
        return this.building.getName();
    }

    @Override
    public String toString() {
        return this.roomId + "--" + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return capacity == room.capacity
                && whiteboard == room.whiteboard
                && tv == room.tv
                && roomId.equals(room.roomId)
                && name.equals(room.name)
                && building.equals(room.building)
                && type.equals(room.type);
    }
}
