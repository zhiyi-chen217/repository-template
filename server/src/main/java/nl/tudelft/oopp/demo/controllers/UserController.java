package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Users;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * get the admin user account
     * @return admin user
     */
    @GetMapping("quote")
    @ResponseBody
    public Optional<Users> getUser() {
        userRepository.save(new Users("admin","","12345") );
        return userRepository.findByUserId("admin");
    }
}
