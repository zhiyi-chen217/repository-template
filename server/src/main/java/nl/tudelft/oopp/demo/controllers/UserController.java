package nl.tudelft.oopp.demo.controllers;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


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


}
