package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubTask(Epic epic, SubTask subTask);

    void deleteEpic(int id);

    void deleteTask(int id);

    void deleteSubTask(int id);

    void deleteAllTask();

    void deleteAllEpic();

    void deleteSubTaskFromEpic(Epic epic);

    void deleteAllSubTask();

    ArrayList<Task> getAllTask();

    ArrayList<Task> getAllEpic();

    ArrayList<Task> getAllSubTask();

    List<Task> getSubTaskForEpic(Epic epic);

    void updateTask(Task task);

    Epic getEpic(int id);

    Task getTask(int id);

    SubTask getSubTask(int id);

    List<Task> getHistory();
}
