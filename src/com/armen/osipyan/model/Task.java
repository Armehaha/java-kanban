package com.armen.osipyan.model;

import com.armen.osipyan.service.Manager;

public class Task {
    private final long id;
    private String name;
    private String formulation;
    private Status status;

    public Task(Task task) {
        this.id = Manager.id++;
        this.name = task.name;
        this.formulation = task.formulation;
        this.status = Status.NEW;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(long id, String name, String formulation, Status status) {
        this.id = id;
        this.name = name;
        this.formulation = formulation;
        this.status = status;
    }

    public long getId() {
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
