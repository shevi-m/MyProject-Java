package org.example.demo.dto;

import org.example.demo.Model.Thought;

import java.util.List;

public class CategoryDTO {
    private long id;
    private String categoryName;
    private String iconPath;
    private String icon;
    private List<Thought> thoughtsList;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Thought> getThoughtsList() {
        return thoughtsList;
    }

    public void setThoughtsList(List<Thought> thoughtsList) {
        this.thoughtsList = thoughtsList;
    }

}

