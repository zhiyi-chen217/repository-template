package nl.tudelft.oopp.demo.communication.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @Test
    public void testRandomQuote() {
        assertNotNull(ServerCommunication.getQuote());
    }
}
