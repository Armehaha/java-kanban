package com.armen.osipyan.model;


public class Task {
    private final int id;
    private String name;
    private String formulation;
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
        this.formulation = formulation;
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getFormulation() {
        return formulation;
    }


    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formulation='" + formulation + '\'' +
                '}';
    }
}
