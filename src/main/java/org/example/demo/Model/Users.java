package org.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Users {
    @Id
    @GeneratedValue
    private long id;
    private String userName;
    @NotBlank(message = "הסיסמה לא יכולה להיות ריקה.")
    private String password;
    @NotBlank(message = "שדה המייל הוא חובה.")
    @Email(message = "אנא הכנס כתובת מייל חוקית.")
    private String email;
    private String imagePath;
    @ManyToMany
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Thought> thoughtsList;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Response> responsesList;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Likes> likesList;


    //בנאי מלא

    public Users(long id, String userName, String password, String email, String imagePath, Set<Role> roles, List<Thought> thoughtsList, List<Response> responsesList, List<Likes> likesList) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.imagePath = imagePath;
        this.roles = roles;
        this.thoughtsList = thoughtsList;
        this.responsesList = responsesList;
        this.likesList = likesList;
    }


    //בנאי ריק

    public Users() {
    }

    //גטרס

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Thought> getThoughtsList() {
        return thoughtsList;
    }

    public List<Response> getResponsesList() {
        return responsesList;
    }

    public List<Likes> getLikesList() {
        return likesList;
    }
    //סטרס

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setThoughtsList(List<Thought> thoughtsList) {
        this.thoughtsList = thoughtsList;
    }

    public void setResponsesList(List<Response> responsesList) {
        responsesList = responsesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }
}


