package com.armen.osipyan.model;

import com.armen.osipyan.service.Manager;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<SubTask> subTasks = new ArrayList<>();

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public Epic(String name, String formulation) {
        super(name, formulation);
    }

    public void addSubTasks(SubTask subTask) {
        subTasks.add(subTask);
        subTask.setEpic(this);
        Manager.longSubTaskHashMap.put(subTask.getId(), subTask);
    }

    public Epic(Epic epic) {
        super(epic);

    }

    public Epic() {


    }
}
