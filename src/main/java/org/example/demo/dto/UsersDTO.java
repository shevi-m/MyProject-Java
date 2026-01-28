package org.example.demo.dto;

import org.example.demo.Model.Likes;
import org.example.demo.Model.Response;
import org.example.demo.Model.Role;
import org.example.demo.Model.Thought;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersDTO {
    private long id;
    private String userName;
    private String password;
    private String email;
    private String imagePath;
    private String image;
    private Set<Role> roles = new HashSet<>();
    private List<Thought> thoughtsList;
    private List<Response> responsesList;
    private List<Likes> likesList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Thought> getThoughtsList() {
        return thoughtsList;
    }

    public void setThoughtsList(List<Thought> thoughtsList) {
        this.thoughtsList = thoughtsList;
    }

    public List<Response> getResponsesList() {
        return responsesList;
    }

    public void setResponsesList(List<Response> responsesList) {
        this.responsesList = responsesList;
    }

    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }
}
