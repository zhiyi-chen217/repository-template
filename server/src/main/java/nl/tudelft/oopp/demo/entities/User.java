package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "type")
    private String type;

    @Column(name = "password")
    private String password;

    /**Constructor of the User class.
     * @param userId is the id of the user
     * @param email is the email address of the user
     * @param password is the password of the user
     */
    public User(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.type = "Student";
        this.password = password;
    }

    public User() {
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
        this.email = email;
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
        User user = (User) o;
        return userId.equals(user.userId);
    }

}
