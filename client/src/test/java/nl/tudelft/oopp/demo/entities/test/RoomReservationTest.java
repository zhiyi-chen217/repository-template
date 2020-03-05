package nl.tudelft.oopp.demo.entities.test;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RoomReservationTest {

    RoomReservation res;
    LocalDateTime locb;
    LocalDateTime locm;
    LocalDateTime loce;
    Room room1;
    Room room2;

    @BeforeEach
    void before() {
        locb = LocalDateTime.of(2020, Month.APRIL, 20, 16, 0);
        locm = LocalDateTime.of(2020, Month.APRIL, 20, 18, 0);
        loce = LocalDateTime.of(2020, Month.APRIL, 20, 19, 0);
        room1 = new Room("A3", "Zimmer", 20,true, true,
                new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                        LocalTime.of(16, 0), 50, "/pics"),
                "Cool description", "/pics");
        room2 = new Room("A3", "Zimmer", 20,true, true,
                new Building("Gebouw", "Mekelweg 4", LocalTime.of(11, 0),
                        LocalTime.of(16, 0), 50, "/pics"),
                "Cool description", "/pictures");
        res = new RoomReservation("Harry", locb, loce, room1, (long) 249);
    }

    @Test
    void getId() {
        assertEquals(249, res.getId());
        assertNotEquals(248, res.getId());
        assertNotNull(res.getId());
    }

    @Test
    void setId() {
        res.setId((long) 248);
        assertEquals(248, res.getId());
        assertNotEquals(249, res.getId());
        assertNotNull(res.getId());
    }

    @Test
    void getUser() {
        assertEquals("Harry", res.getUser());
        assertNotEquals("Bert", res.getUser());
        assertNotNull(res.getUser());
    }

    @Test
    void setUser() {
        res.setUser("Bert");
        assertEquals("Bert", res.getUser());
        assertNotEquals("Harry", res.getUser());
        assertNotNull(res.getUser());
    }

    @Test
    void getBeginTime() {
        assertEquals(locb, res.getBeginTime());
        assertNotEquals(loce, res.getBeginTime());
        assertNotNull(res.getBeginTime());
    }

    @Test
    void setBeginTime() {
        res.setBeginTime(locm);
        assertEquals(locm, res.getBeginTime());
        assertNotEquals(locb, res.getBeginTime());
        assertNotNull(res.getBeginTime());
    }

    @Test
    void getEndTime() {
        assertEquals(loce, res.getEndTime());
        assertNotEquals(locb, res.getEndTime());
        assertNotNull(res.getEndTime());
    }

    @Test
    void setEndTime() {
        res.setEndTime(locm);
        assertEquals(locm, res.getEndTime());
        assertNotEquals(locb, res.getEndTime());
        assertNotNull(res.getEndTime());
    }

    @Test
    void getRoom() {
        assertEquals(room1, res.getRoom());
        assertNotEquals(room2, res.getRoom());
        assertNotNull(res.getRoom());
    }

    @Test
    void setRoom() {
        res.setRoom(room2);
        assertEquals(room2, res.getRoom());
        assertNotEquals(room1, res.getRoom());
        assertNotNull(res.getRoom());
    }

    @Test
    void getBeginTimeString() {
        assertEquals("2020-04-20\n16:00", res.getBeginTimeString());
        assertNotEquals("2020-03-20\n16:00", res.getBeginTimeString());
        assertNotNull(res.getBeginTimeString());
    }

    @Test
    void getEndTimeString() {
        assertEquals("2020-04-20\n19:00", res.getEndTimeString());
        assertNotEquals("2020-03-20\n16:00", res.getEndTimeString());
        assertNotNull(res.getEndTimeString());
    }

    @Test
    void testEquals() {
        RoomReservation reseq1 = new RoomReservation("Harry", locm, loce, room1, (long) 249);
        RoomReservation reseq2 = res;
        Room roomeq = new Room("A3", "Zimmer", 20,true, true,
                new Building("Gebouw", "Mekelweg 4", LocalTime.of(12, 0),
                        LocalTime.of(16, 0), 50, "/pics"),
                "Cool description", "/pictures");
        RoomReservation reseq3 = new RoomReservation("Harry", locm, loce, roomeq, (long) 249);
        assertNotEquals(res, reseq1);
        assertEquals(res, reseq2);
        assertNotEquals(res, roomeq);
        assertEquals(res, res);
    }
}