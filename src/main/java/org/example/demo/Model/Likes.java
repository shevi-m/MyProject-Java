package org.example.demo.Model;

import jakarta.persistence.*;

@Entity
public class Likes {


    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Thought thought;
    @ManyToOne
    private Users user;

    //בנאי מלא


    public Likes(Users user, Thought thought) {
        this.user = user;
        this.thought = thought;
    }

    //בנאי ריק
    public Likes() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Thought getThought() {
        return thought;
    }

    public void setThought(Thought thought) {
        this.thought = thought;
    }

    public Users getUsers() {
        return user;
    }

    public void setUsers(Users user) {
        this.user = user;
    }
}
