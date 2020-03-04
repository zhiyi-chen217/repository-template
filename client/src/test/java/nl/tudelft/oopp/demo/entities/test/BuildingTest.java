package nl.tudelft.oopp.demo.entities.test;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @BeforeEach
    void before() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
    }

    @Test
    void getName() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals("Gebouw", gebouw.getName());
        assertNotEquals("Building", gebouw.getName());
        assertNotNull(gebouw.getName());
    }

    @Test
    void setName() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        gebouw.setName("Building");
        assertEquals("Building", gebouw.getName());
        assertNotEquals("Gebouw", gebouw.getName());
        assertNotNull(gebouw.getName());
    }

    @Test
    void getLocation() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals("Mekelweg 4", gebouw.getLocation());
        assertNotEquals("Mekelweg 5", gebouw.getLocation());
        assertNotNull(gebouw.getLocation());
    }

    @Test
    void setLocation() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        gebouw.setLocation("Mekelweg 5");
        assertEquals("Mekelweg 5", gebouw.getLocation());
        assertNotEquals("Mekelweg 4", gebouw.getLocation());
        assertNotNull(gebouw.getLocation());
    }

    @Test
    void getOpeningHour() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals(LocalTime.of(11, 0), gebouw.getOpeningHour());
        assertNotEquals(LocalTime.of(11, 30), gebouw.getOpeningHour());
        assertNotNull(gebouw.getOpeningHour());
    }

    @Test
    void setOpeningHour() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        gebouw.setOpeningHour(LocalTime.of(12, 0));
        assertEquals(LocalTime.of(12, 0), gebouw.getOpeningHour());
        assertNotEquals(LocalTime.of(12, 30), gebouw.getOpeningHour());
        assertNotNull(gebouw.getOpeningHour());
    }

    @Test
    void getClosingHour() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals(LocalTime.of(16, 0), gebouw.getClosingHour());
        assertNotEquals(LocalTime.of(11, 0), gebouw.getClosingHour());
        assertNotNull(gebouw.getClosingHour());
    }

    @Test
    void setClosingHour() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        gebouw.setClosingHour(LocalTime.of(17, 0));
        assertEquals(LocalTime.of(17, 0), gebouw.getClosingHour());
        assertNotEquals(LocalTime.of(16, 0), gebouw.getClosingHour());
        assertNotNull(gebouw.getClosingHour());
    }

    @Test
    void getPicturesPath() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals("/pics", gebouw.getPicturesPath());
        assertNotEquals("/pictures", gebouw.getPicturesPath());
        assertNotNull(gebouw.getPicturesPath());
    }

    @Test
    void setPicturesPath() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        gebouw.setPicturesPath("/pictures");
        assertEquals("/pictures", gebouw.getPicturesPath());
        assertNotEquals("/pics", gebouw.getPicturesPath());
        assertNotNull(gebouw.getPicturesPath());
    }

    @Test
    void getBikes() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals(50, gebouw.getBikes());
        assertNotEquals(1000000, gebouw.getBikes());
        assertNotNull(gebouw.getBikes());
    }

    @Test
    void setBikes() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        gebouw.setBikes(1000000);
        assertEquals(1000000, gebouw.getBikes());
        assertNotEquals(50, gebouw.getBikes());
        assertNotNull(gebouw.getBikes());
    }

    @Test
    void testToString() {
        Building gebouw = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        assertEquals("Gebouw", gebouw.toString());
        assertNotEquals("Building", gebouw.toString());
        assertNotNull(gebouw.toString());
    }

    @Test
    void testEquals() {
        Building gebouw1 = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        Building gebouw2 = new Building("Gebouw", "Mekelweg 5",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        Building gebouw3 = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(15, 0), 50, "/pics");
        Building gebouw4 = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 500, "/pics");
        Building gebouw5 = new Building("Gebouw", "Mekelweg 4",
                LocalTime.of(11, 0), LocalTime.of(16, 0), 50, "/pics");
        String str = new String("Gebouw");
        assertTrue(gebouw1.equals(gebouw5));
        assertFalse(gebouw1.equals(gebouw2));
        assertFalse(gebouw1.equals(gebouw3));
        assertFalse(gebouw1.equals(gebouw4));
        assertTrue(gebouw1.equals(gebouw1));
        assertFalse(gebouw1.equals(str));
    }
}