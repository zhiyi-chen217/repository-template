package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "Email")
    private String Email;

    @Column(name = "type")
    private String type;

    @Column(name = "password")
    private String password;

    public Users(String user_id, String e_mail, String password){
        this.userId = user_id;
        this.Email = e_mail;
        this.type = "Student";
        this.password = password;
    }
    public Users(){}

    public String getUser_id(){
        return this.userId;
    }

    public String getType(){
        return this.type;
    }

    public String getE_mail(){
        return this.Email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userId.equals(users.userId);
    }

}
