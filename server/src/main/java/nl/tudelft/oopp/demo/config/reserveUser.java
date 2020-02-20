package nl.tudelft.oopp.demo.config;

import nl.tudelft.oopp.demo.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class reserveUser implements UserDetails {
    private String pass;
    private String userName;
    private List<GrantedAuthority> authorities;

    public reserveUser(Users user){
        this.userName = user.getUser_id();
        this.pass = user.getPassword();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getType()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.pass;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
