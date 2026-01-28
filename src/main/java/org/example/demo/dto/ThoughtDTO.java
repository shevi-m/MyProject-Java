package org.example.demo.dto;


import org.example.demo.Model.Age;
import org.example.demo.Model.Likes;
import org.example.demo.Model.Response;

import java.time.LocalDate;
import java.util.List;


public class ThoughtDTO {
    private long id;
    private String title;
    private String imagePath;
    private String image;
    private String desc;
    private LocalDate date;
    private List<Response> responseList;
    private UsersDTO userDTO;
    private CategoryDTO categoryDTO;
    private Age age;
    private List<Likes> likesList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Response> getResponseList() { return responseList; }

    public void setResponseList(List<Response> responseList) { this.responseList = responseList; }

    public UsersDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UsersDTO userDTO) {
        this.userDTO = userDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }


    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }

}

