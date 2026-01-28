package org.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Thought {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "הגיג חייב להכיל כותרת")
    private String title;
    private String imagePath;
    @NotBlank(message = "הגיג חייב להכיל תיאור")
    private String desc;
    private LocalDate date;
    @ManyToOne
    private Users user;
    @NotNull(message = "יש לבחור קטגוריה")
    @ManyToOne
    private Category category;
    @NotNull(message = "יש לבחור טווח גילאים")
    @ManyToOne
    private Age age;
    @OneToMany(mappedBy = "thought" , cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Response> responseList;
    @OneToMany(mappedBy = "thought", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Likes> likesList;

    //בנאי מלא

    public Thought(List<Likes> likesList, List<Response> responseList, Age age, Category category, Users user, LocalDate date, String desc, String imagePath, String title, long id) {
        this.likesList = likesList;
        this.responseList = responseList;
        this.age = age;
        this.category = category;
        this.user = user;
        this.date = date;
        this.desc = desc;
        this.imagePath = imagePath;
        this.title = title;
        this.id = id;
    }


    //בנאי ריק

    public Thought() {
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }

}


