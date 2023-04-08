package com.armen.osipyan.model;

import java.util.ArrayList;

public class Epic extends Task{
    private final ArrayList<SubTask> subTasks = new ArrayList<>();

    public void addSubTasks(SubTask subTask){
        subTasks.add(subTask);
        subTask.setEpic(this);
    }

    public Epic(String name, String formulation) {
        super(name, formulation);

    }
}
