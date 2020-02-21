package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "email")
    private String Email;

    @Column(name = "type")
    private String type;

    @Column(name = "password")
    private String password;

    public User(String user_id, String e_mail, String password){
        this.userId = user_id;
        this.Email = e_mail;
        this.type = "Student";
        this.password = password;
    }
    public User(){}

    public String getUser_id(){
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getE_mail(){
        return this.Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

}
