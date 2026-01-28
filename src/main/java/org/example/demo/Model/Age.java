package org.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Age {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ageName;
    @OneToMany(mappedBy = "age")
    @JsonIgnore
    private List<Thought> thoughtList;

    //בנאי מלא
    public Age(long id ,String ageName, List<Thought> thoughtList) {
        this.id = id;
        this.ageName=ageName;
        this.thoughtList = thoughtList;
    }

    //    בנאי ריק
    public Age() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAgeName() {
        return ageName;
    }

    public void setAgeName(String ageName) {
        this.ageName = ageName;
    }

    public List<Thought> getThoughtList() {
        return thoughtList;
    }

    public void setThoughtList(List<Thought> thoughtList) {
        this.thoughtList = thoughtList;
    }
}