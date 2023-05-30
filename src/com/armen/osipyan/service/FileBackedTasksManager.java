package com.armen.osipyan.service;


import com.armen.osipyan.exception.ManagerSaveException;
import com.armen.osipyan.model.*;
import com.armen.osipyan.util.Formatter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.List;

import static com.armen.osipyan.util.Formatter.historyFromString;
import static com.armen.osipyan.util.Formatter.historyToString;
import static java.nio.file.StandardOpenOption.APPEND;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final Path path;

    public FileBackedTasksManager(Path path) throws FileNotFoundException {
        this.path = path;
    }

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(Paths.get("resource", "\\tasks.csv"));
        System.out.println(fileBackedTasksManager.getHistory());
        System.out.println(fileBackedTasksManager.getAllEpic());
        System.out.println(fileBackedTasksManager.getHistory());

    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(Epic epic, SubTask subTask) {
        super.createSubTask(epic, subTask);
        save();
    }

    @Override
    public void deleteSubTask(int idSubTask) {
        super.deleteSubTask(idSubTask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteSubTaskFromEpic(Epic epic) {
        super.deleteSubTaskFromEpic(epic);
        save();
    }

    @Override
    public List<Task> getSubTaskForEpic(Epic epic) {
        save();
        return super.getSubTaskForEpic(epic);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = super.getSubTask(id);
        save();
        return subTask;
    }


    private void save() {
        try {

            Files.deleteIfExists(path);
            Path test = Files.createFile(path);
            Files.writeString(test, "id,type,name,status,description,epic\n", APPEND);
            for (Task task : longTaskHashMap.values()) {

                Files.writeString(path, Formatter.toString(task) + "\n", APPEND);
            }
            for (Task epic : longEpicHashMap.values()) {
                Files.writeString(path, Formatter.toString(epic) + "\n", APPEND);
            }
            for (Task subTask : longSubTaskHashMap.values()) {
                Files.writeString(path, Formatter.toString(subTask) + "\n", APPEND);
            }
            if (historyManagers.getHistory().size() != 0) {
                Files.writeString(path, "\n" + historyToString(historyManagers), APPEND);
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }


    //пока нет идей как сократить этот огромный метод к сожалению
    public static FileBackedTasksManager loadFromFile(Path oldPath) {

        try {
            List<String> lineList = Files.readAllLines(oldPath);
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(oldPath);
            for (int i = 1; i < lineList.size() - 2; i++) {
                String[] taskFromFile = lineList.get(i).split(",");
                switch (TaskType.valueOf(taskFromFile[1])) {
                    case TASK:
                        fileBackedTasksManager.longTaskHashMap.put(Integer.valueOf(taskFromFile[0]),
                                new Task(Integer.parseInt(taskFromFile[0]),
                                        taskFromFile[2], taskFromFile[4], Status.valueOf(taskFromFile[3])));
                        break;
                    case EPIC:
                        fileBackedTasksManager.longEpicHashMap.put(Integer.valueOf(taskFromFile[0]),
                                new Epic(Integer.parseInt(taskFromFile[0]),
                                        taskFromFile[2], taskFromFile[4], Status.valueOf(taskFromFile[3])));
                        break;
                    case SUBTASK:
                        SubTask subTask = new SubTask(Integer.parseInt(taskFromFile[0]),
                                taskFromFile[2], taskFromFile[4], Status.valueOf(taskFromFile[3]));

                        subTask.setEpic(fileBackedTasksManager.longEpicHashMap.get(Integer.valueOf(taskFromFile[5])));
                        subTask.getEpic().getSubTasks().add(subTask);
                        fileBackedTasksManager.longSubTaskHashMap.put(Integer.valueOf(taskFromFile[0]), subTask);
                        break;
                }
            }
            List<Integer> historyId = historyFromString(lineList.get(lineList.size() - 1));
            for (Integer i : historyId) {
                if (fileBackedTasksManager.longTaskHashMap.containsKey(i)) {
                    fileBackedTasksManager.historyManagers.add(fileBackedTasksManager.longTaskHashMap.get(i));
                } else if (fileBackedTasksManager.longEpicHashMap.containsKey(i)) {
                    System.out.println(fileBackedTasksManager.historyManagers.getHistory());
                    fileBackedTasksManager.historyManagers.add(fileBackedTasksManager.longEpicHashMap.get(i));

                } else if (fileBackedTasksManager.longSubTaskHashMap.containsKey(i)) {
                    fileBackedTasksManager.historyManagers.add(fileBackedTasksManager.longSubTaskHashMap.get(i));
                }

            }
            return fileBackedTasksManager;
        } catch (IOException e) {
            System.out.println("Файла с таким названием нет");
        }

        return null;
    }


}
