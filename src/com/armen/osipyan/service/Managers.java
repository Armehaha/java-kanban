package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.HashMap;



public class Managers {
    public static int id = 1;
    protected static final HashMap<Integer, Task> longTaskHashMap = new HashMap<>();
    protected static final HashMap<Integer, Epic> longEpicHashMap = new HashMap<>();
    protected static final HashMap<Integer, SubTask> longSubTaskHashMap = new HashMap<>();

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }


    //не особо понимаю где конкретно создавать этот объект, сейчас создаю его в InMemoryTaskManager
    // и так же при вызове метода getDefault
    public static HistoryManagers getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
