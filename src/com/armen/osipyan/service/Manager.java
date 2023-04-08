package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.HashMap;

public class Manager {
    public static long id = 1;
    public static HashMap<Long, Task> longTaskHashMap = new HashMap<>();
    public static HashMap<Long, Epic> longEpicHashMap = new HashMap<>();

    public static HashMap<Long, SubTask> longSubTaskHashMap = new HashMap<>();

    public void createTask(Task task) {
        longTaskHashMap.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        longEpicHashMap.put(epic.getId(), epic);
    }

    public void createSubTask(Epic epic, SubTask subTask) {
        epic.addSubTasks(subTask);
    }

    public void deleteSubTask(long idSubTask) {
   longSubTaskHashMap.remove(idSubTask);
    }
    public void deleteTask(long id){
            longTaskHashMap.remove(id);
    }
}
