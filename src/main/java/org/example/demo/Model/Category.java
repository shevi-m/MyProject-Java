package org.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String categoryName;
    private String iconPath;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Thought> thoughtsList;

    //בנאי מלא
    public Category(long id, String categoryName, String iconPath, List<Thought> thoughtsList) {
        this.id = id;
        this.categoryName = categoryName;
        this.iconPath = iconPath;
        this.thoughtsList = thoughtsList;
    }

    //בנאי ריק
    public Category() {
    }

    //גטרס

    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public List<Thought> getThoughtsList() {
        return thoughtsList;
    }

    //סטרס

    public void setId(long id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setThoughtsList(List<Thought> thoughtsList) {
        this.thoughtsList = thoughtsList;
    }
}

