package nl.tudelft.oopp.demo.config;

import java.util.Optional;

import nl.tudelft.oopp.demo.entities.Users;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class reserveUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUserId(username);
        if (user.isPresent()) {
            user.orElseThrow(() -> {
                throw new UsernameNotFoundException("invalid username");
            });
        }
        return new reserveUser(user.get());
    }
}
