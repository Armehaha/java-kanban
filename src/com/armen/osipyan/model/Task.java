package com.armen.osipyan.model;

import com.armen.osipyan.service.Manager;

public class Task {

    private long id;
     private  String name;
    private String formulation;
    private Status status;

    public Task(String name, String formulation) {
        this.id = ++Manager.id;
        this.name = name;
        this.formulation = formulation;
        this.status = Status.NEW;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(String name, String formulation, Status status) {
        this.name = name;
        this.formulation = formulation;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formulation='" + formulation + '\'' +
                '}';
    }

    public void setId(long id) {
        this.id = id;
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

}
