package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.ArrayList;
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
        longEpicHashMap.clear();
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


    //на мой взгляд странно будет удалять просто все подзадачи не обращая внимания на эпики , поэтому сделал два метода
    public void deleteSubTaskFromEpic(Epic epic) {
        epic.getSubTasks().forEach(subTask -> longSubTaskHashMap.remove(subTask.getId()));
        epic.deleteAllSubTasks();
    }


    public void deleteAllSubTask() {
        for (SubTask subTask : longSubTaskHashMap.values()
        ) {
            subTask.getEpic().deleteAllSubTasks();
        }
        longSubTaskHashMap.clear();
    }


    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(longTaskHashMap.values());
    }


    public ArrayList<Task> getAllEpic() {
        return new ArrayList<>(longEpicHashMap.values());
    }


    public ArrayList<Task> getAllSubTask() {
        return new ArrayList<>(longSubTaskHashMap.values());
    }


    public ArrayList<Task> getSubTaskForEpic(Epic epic) {
        return epic.getSubTasks();
    }


    public void updateTask(Task task) {
        if (task != null) {
            longTaskHashMap.put(task.getId(), task);
        } else {
            System.out.println("При обновлении данный произошла ошибка, отправьте корректные данные для обновления.");
        }

    }


    public void updateEpic(Epic epic) {
        if (epic != null) {
            longEpicHashMap.put(epic.getId(), epic);
        } else {
            System.out.println("При обновлении данный произошла ошибка, отправьте корректные данные для обновления.");
        }
    }


    public void updateSubTask(SubTask subTask) {
        if (subTask != null) {
            for (int i = 0; i < subTask.getEpic().getSubTasks().size(); i++) {
                if (subTask.getEpic().getSubTasks().get(i).getId() == subTask.getId()) {
                    subTask.getEpic().getSubTasks().set(i, subTask);
                }
            }
            longSubTaskHashMap.put(subTask.getId(), subTask);
            checkEpicStatus(subTask.getEpic());
        } else {
            System.out.println("При обновлении данный произошла ошибка, отправьте корректные данные для обновления.");
        }

    }


    private void checkEpicStatus(Epic epic) {
        ArrayList<Task> list = epic.getSubTasks();
        for (Task t : list) {
            int count = 0;
            if (t.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
            } else if (t.getStatus() == Status.DONE) {
                count++;
                if (count == list.size()) {
                    epic.setStatus(Status.DONE);
                }
            }
        }
    }

    public Epic getEpic(long id) {
        return longEpicHashMap.get(id);
    }

    public Task getTask(long id) {
        return longTaskHashMap.get(id);
    }

    public SubTask getSubTask(long id) {
        return longSubTaskHashMap.get(id);
    }
}
