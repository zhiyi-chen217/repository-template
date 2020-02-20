package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.demo.entities.Quote;
import nl.tudelft.oopp.demo.entities.Users;
import nl.tudelft.oopp.demo.repositories.QuoteRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.h2.engine.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuoteTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void retrieveUser() {
        Users u1 = new Users("admin", "", "12345");
        userRepository.save(u1);
        assertEquals(u1, userRepository.findByUserId("admin").get());
    }
}