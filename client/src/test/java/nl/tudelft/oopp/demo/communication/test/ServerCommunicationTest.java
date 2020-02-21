package nl.tudelft.oopp.demo.communication.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @Test
    public void testLogIn(){
        assertEquals("hello admin", ServerCommunication.sendLogin("admin", "12345","http://localhost:8080/login"));
    }
}
