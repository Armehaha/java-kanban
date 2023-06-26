package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.armen.osipyan.service.Managers.*;

public class InMemoryTaskManager implements TaskManager {
    public static int id = 1;
    protected final HashMap<Integer, Task> longTaskHashMap = new HashMap<>();
    protected final HashMap<Integer, Epic> longEpicHashMap = new HashMap<>();
    protected final HashMap<Integer, SubTask> longSubTaskHashMap = new HashMap<>();
    protected final HistoryManagers historyManagers = getDefaultHistory();
    protected TreeSet<Task> treeSet = new TreeSet<>
            (Comparator.comparing(Task::getStartTime, Comparator.nullsLast(LocalDateTime::compareTo)));

    @Override
    public void createTask(Task task) {
        task.setId(id++);
        if (checkTime(task)) {
            longTaskHashMap.put(task.getId(), task);
            treeSet.add(task);
        } else {
            id--;
            throw new RuntimeException("В данный момент нельзя добавить задачу");
        }


    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(id++);
        longEpicHashMap.put(epic.getId(), epic);
        treeSet.add(epic);
    }

    @Override
    public void createSubTask(Epic epic, SubTask subTask) {
        if (!longEpicHashMap.containsKey(epic.getId())) {
            createEpic(epic);
        }
        subTask.setId(id++);
        epic.addSubTasks(subTask);
        if (checkTime(epic)) {
            longSubTaskHashMap.put(subTask.getId(), subTask);
            timeEpic(epic.getId());
        } else {
            id--;
            throw new RuntimeException("В данный момент нельзя добавить задачу");
        }

    }

    @Override
    public void deleteSubTask(int idSubTask) {

        longSubTaskHashMap.get(idSubTask).getEpic().getSubTasks().removeIf(subTask -> subTask.getId() == idSubTask);
        treeSet.remove(longSubTaskHashMap.get(idSubTask));
        longSubTaskHashMap.remove(idSubTask);
        historyManagers.remove(idSubTask);

    }

    @Override
    public void deleteTask(int id) {
        treeSet.remove(longTaskHashMap.get(id));
        longTaskHashMap.remove(id);
        historyManagers.remove(id);
    }


    @Override
    public void deleteEpic(int id) {
        longEpicHashMap.get(id).getSubTasks().forEach(subTask -> longSubTaskHashMap.remove(subTask.getId()));
        longEpicHashMap.get(id).getSubTasks().forEach(subTask -> treeSet.remove(subTask));
        treeSet.remove(longEpicHashMap.get(id));
        longEpicHashMap.remove(id);
        historyManagers.remove(id);
    }

    @Override
    public void deleteAllTask() {
        for (Task task : longTaskHashMap.values()) {
            historyManagers.remove(task.getId());
            treeSet.remove(task);
        }
        longTaskHashMap.clear();
    }

    @Override
    public void deleteAllEpic() {
        for (Task task : longSubTaskHashMap.values()) {
            historyManagers.remove(task.getId());
            treeSet.remove(task);
        }
        for (Task task : longEpicHashMap.values()) {
            historyManagers.remove(task.getId());
            treeSet.remove(task);
        }
        longSubTaskHashMap.clear();
        longEpicHashMap.clear();
    }

    @Override
    public void deleteSubTaskFromEpic(Epic epic) {
        epic.getSubTasks().forEach(subTask -> {
            longSubTaskHashMap.remove(subTask.getId());
            historyManagers.remove(subTask.getId());
            treeSet.remove(subTask);
        });
        epic.deleteAllSubTasks();
    }

    @Override
    public void deleteAllSubTask() {
        for (Task task : longSubTaskHashMap.values()) {
            historyManagers.remove(task.getId());
            treeSet.remove(task);
        }
        for (SubTask subTask : longSubTaskHashMap.values()
        ) {
            subTask.getEpic().deleteAllSubTasks();
            treeSet.remove(subTask);
        }
        longSubTaskHashMap.clear();
    }

    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(longTaskHashMap.values());
    }

    @Override
    public List<Task> getAllEpic() {
        return new ArrayList<>(longEpicHashMap.values());
    }

    @Override
    public List<Task> getAllSubTask() {
        return new ArrayList<>(longSubTaskHashMap.values());
    }

    @Override
    public List<Task> getSubTaskForEpic(Epic epic) {
        return epic.getSubTasks();
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            if (task instanceof SubTask) {
                SubTask subTask = (SubTask) task;
                treeSet.remove(task);

                for (int i = 0; i < subTask.getEpic().getSubTasks().size(); i++) {
                    if (subTask.getEpic().getSubTasks().get(i).getId() == subTask.getId()) {
                        subTask.getEpic().getSubTasks().set(i, subTask);
                    }
                }
                longSubTaskHashMap.put(subTask.getId(), subTask);
                checkEpicStatus(subTask.getEpic());
                treeSet.add(subTask);
            } else if (task instanceof Epic) {
                Epic epic = (Epic) task;
                longEpicHashMap.put(epic.getId(), epic);
                treeSet.remove(task);
                timeEpic(epic.getId());
                treeSet.add(epic);
            } else {
                longTaskHashMap.put(task.getId(), task);
                treeSet.remove(task);
                treeSet.add(task);

            }
        } else {
            System.out.println("При обновлении данный произошла ошибка, отправьте корректные данные для обновления.");
        }
    }

    private void checkEpicStatus(Epic epic) {
        List<Task> list = epic.getSubTasks();
        int count = 0;
        for (Task t : list) {

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

    public ArrayList<Task> getTasksCompare() {
        return new ArrayList<>(treeSet);
    }

    public boolean checkTime(Task task) {
        int count = 0;
        if (getTasksCompare().size() == 0 || task.getStartTime() == null) {
            return true;
        }
        for (Task prioritizedTask : getTasksCompare()) {
            if (!(task.getStartTime().isAfter(prioritizedTask.getEndTime()))) {
                count++;
                if (task.getId() == prioritizedTask.getId()) {
                    count--;
                }
            }
        }
        return count == 0;
    }

    public void timeEpic(int id) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        Duration duration = Duration.ZERO;
        Epic epic = longEpicHashMap.get(id);
        List<Task> st = epic.getSubTasks();
        if (st.size() != 0) {
            for (Task task : st) {
                if (epic.getSubTasks().size() == 1) {
                    startTime = task.getStartTime();
                    endTime = task.getEndTime();
                    duration = task.getDuration();
                } else {
                    if (startTime == null){
                        startTime = task.getStartTime();
                    }
                    if (task.getStartTime().isBefore(startTime)) {
                        startTime = task.getStartTime();
                    }
                    if (endTime == null){
                        endTime = task.getEndTime();
                    }
                    if (task.getEndTime().isAfter(endTime)) {
                        endTime = task.getEndTime();
                    }
                    duration = duration.plus(task.getDuration());
                }
            }
        }
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(duration);


    }
}

