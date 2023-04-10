package com.armen.osipyan.model;

import com.armen.osipyan.service.Manager;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Task> subTasks;

    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public Epic(long id, String name, String formulation, Status status) {
        super(id, name, formulation, status);
        subTasks = new ArrayList<>();
    }

    public void addSubTasks(SubTask subTask) {
        subTasks.add(subTask);
        subTask.setEpic(this);
        Manager.longSubTaskHashMap.put(subTask.getId(), subTask);
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", formulation='" + this.getFormulation() + '\'' +
                "subTasks=" + subTasks.toString() +
                '}';
    }
}
