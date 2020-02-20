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
    @GetMapping("admin")
    @ResponseBody
    public String getUser() {
        userRepository.save(new Users("admin","","12345") );
        Optional<Users> user = userRepository.findByUserId("admin");
        if(user.isPresent()){
            return user.get().getUser_id();
        }
        return "This user was not found.";
    }
}
