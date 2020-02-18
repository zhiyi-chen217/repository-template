package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "Email")
    private String e_mail;

    @Column(name = "type")
    private String type;

    @Column(name = "Password")
    private String password;

    public Users(String user_id, String e_mail, String password){
        this.user_id = user_id;
        this.e_mail = e_mail;
        this.type = "Student";
        this.password = password;
    }
    public Users(){}

    public String getUser_id(){
        return this.user_id;
    }

    public String getType(){
        return this.type;
    }

    public String getE_mail(){
        return this.e_mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return user_id.equals(users.user_id);
    }

}
