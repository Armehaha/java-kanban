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
        longSubTaskHashMap.get(idSubTask).getEpic().getSubTasks().removeIf(subTask -> subTask.getId() == idSubTask);
        longSubTaskHashMap.remove(idSubTask);
    }

    public void deleteTask(long id) {
        longTaskHashMap.remove(id);
    }

    public void deleteEpic(long id) {
        longEpicHashMap.get(id).getSubTasks().forEach(subTask -> longSubTaskHashMap.remove(subTask.getId()));
        longEpicHashMap.remove(id);
    }


    public void deleteAllTask() {
        longTaskHashMap.clear();
    }

    public void deleteAllEpic() {
        longSubTaskHashMap.clear();
        longEpicHashMap.clear();
    }

    //на мой взгляд как то странно делать метод который удаляет просто все подзадачи всех эпиков,
    // поэтому я удалию подзадачи определенного эпика(надеюсь это правильно)
    public void deleteSubTaskFromEpic(Epic epic) {
        epic.getSubTasks().forEach(subTask -> longSubTaskHashMap.remove(subTask.getId()));
        epic.deleteAllSubTasks();

    }


}
