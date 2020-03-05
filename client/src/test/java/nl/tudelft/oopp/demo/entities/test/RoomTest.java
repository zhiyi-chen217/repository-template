package nl.tudelft.oopp.demo.entities.test;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room zimmer;

    @BeforeEach
    void before() {
        zimmer = new Room("A3", "Zimmer", 20, true, true,
                new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                        LocalTime.of(16, 0), 50, "/pics"), "Coop description", "/pics");
    }
    @Test
    void getRoomId() {
        assertEquals("A3", zimmer.getRoomId());
        assertNotEquals("A4", zimmer.getRoomId());
        assertNotNull(zimmer.getRoomId());
    }

    @Test
    void setRoomId() {
        zimmer.setRoomId("A4");
        assertEquals("A4", zimmer.getRoomId());
        assertNotEquals("A3", zimmer.getRoomId());
        assertNotNull(zimmer.getRoomId());
    }

    @Test
    void getName() {
        assertEquals("Zimmer", zimmer.getName());
        assertNotEquals("Room", zimmer.getName());
        assertNotNull(zimmer.getName());
    }

    @Test
    void setName() {
        zimmer.setName("Room");
        assertEquals("Room", zimmer.getName());
        assertNotEquals("Zimmer", zimmer.getName());
        assertNotNull(zimmer.getName());
    }

    @Test
    void getCapacity() {
        assertEquals(20, zimmer.getCapacity());
        assertNotEquals(30, zimmer.getCapacity());
    }

    @Test
    void setCapacity() {
        zimmer.setCapacity(30);
        assertEquals(30, zimmer.getCapacity());
        assertNotEquals(20, zimmer.getCapacity());
    }

    @Test
    void getDescription() {
        assertEquals("Coop description", zimmer.getDescription());
        assertNotEquals("Cool description", zimmer.getDescription());
        assertNotNull(zimmer.getDescription());
    }

    @Test
    void setDescription() {
        zimmer.setDescription("Cool description");
        assertEquals("Cool description", zimmer.getDescription());
        assertNotEquals("Coop description", zimmer.getDescription());
        assertNotNull(zimmer.getDescription());
    }

    @Test
    void getType() {
        assertEquals("AllCanUse", zimmer.getType());
        assertNotEquals("Employee", zimmer.getType());
        assertNotNull(zimmer.getType());
    }

    @Test
    void setType() {
        zimmer.setType(true);
        assertEquals("Employee", zimmer.getType());
        assertNotEquals("AllCanUse", zimmer.getType());
        assertNotNull(zimmer.getType());
    }

    @Test
    void getPicturesPath() {
        assertEquals("/pics", zimmer.getPicturesPath());
        assertNotEquals("/pictures", zimmer.getPicturesPath());
        assertNotNull(zimmer.getPicturesPath());
    }

    @Test
    void setPicturesPath() {
        zimmer.setPicturesPath("/pictures");
        assertEquals("/pictures", zimmer.getPicturesPath());
        assertNotEquals("/pics", zimmer.getPicturesPath());
        assertNotNull(zimmer.getPicturesPath());
    }

    @Test
    void isWhiteboard() {
        assertTrue(zimmer.isWhiteboard());
    }

    @Test
    void setWhiteboard() {
        zimmer.setWhiteboard(false);
        assertFalse(zimmer.isWhiteboard());
    }

    @Test
    void isTv() {
        assertTrue(zimmer.isTv());
    }

    @Test
    void setTv() {
        zimmer.setTv(false);
        assertFalse(zimmer.isTv());
    }

    @Test
    void getBuilding() {
        Building building1 = new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                LocalTime.of(16, 0), 50, "/pics");
        Building building2 = new Building("Gebouw", "Mekelweg 5", LocalTime.of(11, 0),
                LocalTime.of(16, 0), 50, "/pics");
        Building building3 = null;
        assertEquals(building1, zimmer.getBuilding());
        assertNotEquals(building2, zimmer.getBuilding());
        assertNotEquals(building3, zimmer.getBuilding());
    }

    @Test
    void setBuilding() {
        Building building1 = new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                LocalTime.of(16, 0), 50, "/pics");
        Building building2 = new Building("Gebouw", "Mekelweg 5", LocalTime.of(11, 0),
                LocalTime.of(16, 0), 50, "/pics");
        Building building3 = null;
        zimmer.setBuilding(building2);
        assertEquals(building2, zimmer.getBuilding());
        assertNotEquals(building1, zimmer.getBuilding());
        assertNotEquals(building3, zimmer.getBuilding());
    }

    @Test
    void getBuildingName() {
        assertEquals("Gebouw", zimmer.getBuildingName());
        assertNotEquals("Zimmer", zimmer.getBuildingName());
        assertNotNull(zimmer.getBuildingName());
    }

    @Test
    void testToString() {
        assertEquals("A3--Zimmer", zimmer.toString());
        assertNotEquals("A3--Gebouw", zimmer.toString());
        assertNotNull(zimmer.toString());
    }

    @Test
    void testEquals() {
        Room zimmer1 = zimmer;
        Room zimmer2 = new Room("A3", "Zimmer", 20, true, true,
                new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                        LocalTime.of(16, 0), 50, "/pics"), "Coop description", "/pics");
        Room zimmer3 = new Room("A2", "Zimmer", 20, true, true,
                new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                        LocalTime.of(16, 0), 50, "/pics"), "Coop description", "/pics");
        Room zimmer4 = new Room("A3", "Zimmer", 20, true, true,
                new Building("Gebouw", "Mekelweg 5", LocalTime.of(11, 0),
                        LocalTime.of(16, 0), 50, "/pics"), "Coop description", "/pics");
        assertTrue(zimmer1.equals(zimmer2));
        assertFalse(zimmer1.equals(zimmer3));
        assertFalse(zimmer1.equals(zimmer4));
    }
}