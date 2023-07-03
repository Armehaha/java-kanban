import com.armen.osipyan.http.HttpTaskServer;
import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.FileBackedTasksManager;
import com.armen.osipyan.service.HttpTaskManager;
import com.armen.osipyan.service.Managers;
import com.armen.osipyan.service.TaskManager;


import java.io.IOException;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;



public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //создаю файлового менелджера и далее создаю и просматриваю задачи
//        TaskManager manager = new FileBackedTasksManager(Paths.get("resource","\\tasks.csv"));
//        manager.createTask(new Task(1, "Подготовить скрипты для вывода в прод",
//                "Добавьте новые данные в таблицу nsi_regions", Status.NEW));
//
//        manager.createTask(new Task(1, "добавить настройки услуги",
//                "добавьте в таблицу настрйки для новой услуги", Status.NEW));
//
//        Epic epic = new Epic(1, "Редирект с 10700 на 606204", "редирект с одной услуги на другую", Status.NEW);
//        manager.createEpic(epic);
//        manager.createSubTask(epic, new SubTask(1, "фронт", "фронт", Status.NEW));
//        manager.createSubTask(epic, new SubTask(2, "бэк", "бэк", Status.NEW));
//
//        Epic epic2 = new Epic(3, "обновление услуги", "обновление услуги", Status.NEW);
//        manager.createSubTask(epic2,new SubTask(3, "back", "back", Status.NEW));
//        manager.createEpic(epic2);
//        Epic epic3 = new Epic(3, "обновление услуги", "обновление услуги", Status.NEW);
//        manager.createEpic(epic3);
//        System.out.println(manager.getTask(1));
//        System.out.println(manager.getSubTask(4));
//        System.out.println(manager.getEpic(3));
//
//            //создаю такого же менеджера только уже из существующего файла
//        FileBackedTasksManager fileBackedTasksManager = loadFromFile(Paths.get("resource", "\\tasks.csv"));
//
//        //сравнение обычных задач
//        boolean flag = false;
//        //сравнение всех эпиков
//        System.out.println("\n\n\nЭпики первого менеджера :" +manager.getAllEpic()+"\n"+"Эпики второго менеджера :"+fileBackedTasksManager.getAllEpic());
//        for (int i = 0; i < manager.getAllEpic().size(); i++) {
//            flag = manager.getAllEpic().get(i).equals(fileBackedTasksManager.getAllEpic().get(i));
//            if (!flag){
//                break;
//            }
//        }
//        System.out.println("Одинаковые ли эпики у менеджеров? " + flag);
//        //сравнение простых задач
//        System.out.println("Задачи первого менеджера :" +manager.getAllTask()+"\n"+"Задачи второго менеджера :"+fileBackedTasksManager.getAllTask());
//        for (int i = 0; i < manager.getAllTask().size(); i++) {
//            flag = manager.getAllTask().get(i).equals(fileBackedTasksManager.getAllTask().get(i));
//            if (!flag){
//                break;
//            }
//        }
//        System.out.println("Одинаковые ли задачи у менеджеров? " + flag);
//        //сравнение подзадач
//        System.out.println("Подзадачи первого менеджера :" +manager.getAllSubTask()+"\n"+"Подзадачи второго менеджера :"+fileBackedTasksManager.getAllSubTask());
//
//        for (int i = 0; i < manager.getAllSubTask().size(); i++) {
//            flag = manager.getAllSubTask().get(i).equals(fileBackedTasksManager.getAllSubTask().get(i));
//            if (!flag){
//                break;
//            }
//        }
//        System.out.println("Одинаковые ли подзадачи у менеджеров? " + flag);
//
//        //сревнение истории
//        System.out.println(manager.getHistory().equals(fileBackedTasksManager.getHistory()));
//        Task simpleTask = new Task("Test0", "TestDesc0", Status.NEW);
//
//        manager.createTask(simpleTask);
//        Epic epicTask = new Epic("EpicTest0", "EpicTestDesc0",  Status.NEW);
//
//        manager.createEpic(epicTask);
//        SubTask subtask = new SubTask("SubTest0", "SubDescTest0",  Status.NEW);
//
//        manager.createSubTask(epicTask,subtask);
//        manager.getEpic(epicTask.getId());
//        FileBackedTasksManager newTaskManager = FileBackedTasksManager.loadFromFile(Paths.get("resource","\\tasks.csv"));

//        Epic epicTask = new Epic("epic", "epic1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());
//
//        manager.createEpic(epicTask);
//        manager.getEpic(epicTask.getId());

//        HttpTaskServer httpTaskServer = new HttpTaskServer();
        Task epicTask = new SubTask("epic", "epic1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        System.out.println(epicTask instanceof SubTask);

    }
}
