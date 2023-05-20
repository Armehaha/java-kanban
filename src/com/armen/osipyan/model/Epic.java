package com.armen.osipyan.model;


import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Task> subTasks;

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public Epic(int id, String name, String formulation, Status status) {
        super(id, name, formulation, status);
        subTasks = new ArrayList<>();
    }

    public void addSubTasks(SubTask subTask) {
        subTasks.add(subTask);
        subTask.setEpic(this);
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", formulation='" + this.getDescription() + '\'' +
                "subTasks=" + subTasks.toString() +
                '}';
    }
}
