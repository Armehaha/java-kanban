package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.List;

import static com.armen.osipyan.service.Managers.*;

public class InMemoryTaskManager implements TaskManager {
    final HistoryManagers historyManagers = getDefaultHistory();

    @Override
    public void createTask(Task task) {
        longTaskHashMap.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        longEpicHashMap.put(epic.getId(), epic);
    }

    @Override
    public void createSubTask(Epic epic, SubTask subTask) {
        epic.addSubTasks(subTask);
        longSubTaskHashMap.put(subTask.getId(), subTask);

    }

    @Override
    public void deleteSubTask(int idSubTask) {
        longSubTaskHashMap.get(idSubTask).getEpic().getSubTasks().removeIf(subTask -> subTask.getId() == idSubTask);
        longSubTaskHashMap.remove(idSubTask);
        historyManagers.remove(idSubTask);
    }

    @Override
    public void deleteTask(int id) {
        longTaskHashMap.remove(id);
        historyManagers.remove(id);
    }


    @Override
    public void deleteEpic(int id) {
        longEpicHashMap.get(id).getSubTasks().forEach(subTask -> longSubTaskHashMap.remove(subTask.getId()));
        longEpicHashMap.remove(id);
        historyManagers.remove(id);
    }

    @Override
    public void deleteAllTask() {
        longTaskHashMap.clear();
    }

    @Override
    public void deleteAllEpic() {
        longSubTaskHashMap.clear();
        longEpicHashMap.clear();
    }

    @Override
    //на мой взгляд странно будет удалять просто все подзадачи не обращая внимания на эпики , поэтому сделал два метода
    public void deleteSubTaskFromEpic(Epic epic) {
        epic.getSubTasks().forEach(subTask -> longSubTaskHashMap.remove(subTask.getId()));
        epic.deleteAllSubTasks();
    }

    @Override
    public void deleteAllSubTask() {
        for (SubTask subTask : longSubTaskHashMap.values()
        ) {
            subTask.getEpic().deleteAllSubTasks();
        }
        longSubTaskHashMap.clear();
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(longTaskHashMap.values());
    }

    @Override
    public ArrayList<Task> getAllEpic() {
        return new ArrayList<>(longEpicHashMap.values());
    }

    @Override
    public ArrayList<Task> getAllSubTask() {
        return new ArrayList<>(longSubTaskHashMap.values());
    }

    @Override
    public ArrayList<Task> getSubTaskForEpic(Epic epic) {
        return epic.getSubTasks();
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            if (task instanceof SubTask) {
                SubTask subTask = (SubTask) task;
                for (int i = 0; i < subTask.getEpic().getSubTasks().size(); i++) {
                    if (subTask.getEpic().getSubTasks().get(i).getId() == subTask.getId()) {
                        subTask.getEpic().getSubTasks().set(i, subTask);
                    }
                }
                longSubTaskHashMap.put(subTask.getId(), subTask);
                checkEpicStatus(subTask.getEpic());
            } else if (task instanceof Epic) {
                Epic epic = (Epic) task;
                longEpicHashMap.put(epic.getId(), epic);
            } else {
                longTaskHashMap.put(task.getId(), task);

            }
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

    @Override
    public Epic getEpic(int id) {
        historyManagers.add(longEpicHashMap.get(id));
        return longEpicHashMap.get(id);
    }

    @Override
    public Task getTask(int id) {
        historyManagers.add(longTaskHashMap.get(id));
        return longTaskHashMap.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManagers.add(longSubTaskHashMap.get(id));
        return longSubTaskHashMap.get(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManagers.getHistory();
    }
}

