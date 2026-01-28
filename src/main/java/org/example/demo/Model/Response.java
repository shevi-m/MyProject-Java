package org.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Response {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "תגובה אינה יכולה להישלח ריקה")
    private String content;
    private LocalDate date;
    @ManyToOne
    private Thought thought;
    @ManyToOne
    private Users user;


    //בנאי מלא

    public Response(long id, String content, LocalDate date, Thought thought, Users user) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.thought = thought;
        this.user = user;
    }


    //בנאי ריק

    public Response() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Thought getThought() {
        return thought;
    }

    public void setThought(Thought thought) {
        this.thought = thought;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

