package com.armen.osipyan.model;


public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(int id, String name, String formulation, Status status) {
        this.id = id;
        this.name = name;
        this.description = formulation;
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formulation='" + description + '\'' +
                '}';
    }


}
