package nl.tudelft.oopp.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class LoginController {

    @GetMapping("login")
    public String greetings(Authentication authentication) {
       String userName = authentication.getName();
       return "hello " + userName;
    }
}
