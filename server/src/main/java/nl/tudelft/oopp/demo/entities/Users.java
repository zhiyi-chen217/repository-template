package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "type")
    private String type;

    @Column(name = "password")
    private String password;

    /**Constructor of the Users class.
     *
     */
    public Users(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.type = "Student";
        this.password = password;
    }

    public Users() {
    }

    public String getUser_id() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getE_mail() {
        return this.email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        return userId.equals(users.userId);
    }

}
