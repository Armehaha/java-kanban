package com.armen.osipyan.model;

import com.armen.osipyan.service.Manager;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Task> subTasks;

    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public Epic(String name, String formulation) {
        super(name, formulation);
        subTasks = new ArrayList<>();
    }

    public Epic(long id, String name, String formulation, Status status) {
        super(id, name, formulation);
        super.setStatus(status);
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
