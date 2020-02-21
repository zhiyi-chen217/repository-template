package nl.tudelft.oopp.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.Base64;

@RestController
public class LoginController {

    @GetMapping("login")
    public String greetings(Authentication authentication) {
       String userName = authentication.getName();
       return "hello " + userName;
    }

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
