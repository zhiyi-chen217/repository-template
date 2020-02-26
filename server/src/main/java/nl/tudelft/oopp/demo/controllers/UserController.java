package nl.tudelft.oopp.demo.controllers;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.exceptions.RedundantentityException;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains rest endpoints supporting CRUD operations for user entity.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("login")
    public String greetings(Authentication authentication) throws SQLException {
        String userName = authentication.getName();
        return "hello " + userName;
    }

    /**
     * Welcomes an Admin, if the user is one.
     * @return a welcome message.
     * @throws AccessDeniedException if user is not an admin.
     */

    @GetMapping("login/admin")
    public String greetingsAdmin(Authentication authentication) throws AccessDeniedException {
        String userName = authentication.getName();
        try {
            if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
                throw new AccessDeniedException("You are not an admin");
            }
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "hello Admin " + userName;
    }

    /**
     * This method is the rest endpoint for sign up procedure.
     * @param user the user to be registered
     * @return the generated http response
     * @throws RedundantentityException thrown when the user already exists
     */
    @PostMapping("signup")
    public ResponseEntity signUpUser(@RequestBody User user) throws RedundantentityException {
        Optional<User> temp = userRepository.findById(user.getUser_id());
        if (temp.isPresent()) {
            throw new RedundantentityException("The user already exists");
        }
        user.setType("Student");
        userRepository.save(user);
        return ResponseEntity.accepted().body("saved successfully");
    }
}
