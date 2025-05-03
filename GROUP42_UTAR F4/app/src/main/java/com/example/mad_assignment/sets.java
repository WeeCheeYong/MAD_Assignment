package com.example.mad_assignment;

public class sets {
    private int id;
    private String title;
    private String description;
    private String lastResult;

    public sets() {
    }

    public sets(int id, String title, String description, String lastResult) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lastResult = lastResult;
    }

    public sets(String title, String description) {
        this.title = title;
        this.description = description;
        this.lastResult = null;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }
}
