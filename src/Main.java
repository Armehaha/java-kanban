import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.FileBackedTasksManager;
import com.armen.osipyan.service.Managers;
import com.armen.osipyan.service.TaskManager;


import java.io.IOException;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

import static com.armen.osipyan.service.FileBackedTasksManager.loadFromFile;

public class Main {

    public static void main(String[] args) throws IOException {
        //создаю файлового менелджера и далее создаю и просматриваю задачи
        TaskManager manager = new FileBackedTasksManager(Paths.get("resource","\\tasks.csv"));
        manager.createTask(new Task(1, "Подготовить скрипты для вывода в прод",
                "Добавьте новые данные в таблицу nsi_regions", Status.NEW));

        manager.createTask(new Task(1, "добавить настройки услуги",
                "добавьте в таблицу настрйки для новой услуги", Status.NEW));

        Epic epic = new Epic(1, "Редирект с 10700 на 606204", "редирект с одной услуги на другую", Status.NEW);
        manager.createEpic(epic);
        manager.createSubTask(epic, new SubTask(1, "фронт", "фронт", Status.NEW));
        manager.createSubTask(epic, new SubTask(2, "бэк", "бэк", Status.NEW));

        Epic epic2 = new Epic(3, "обновление услуги", "обновление услуги", Status.NEW);
        epic2.addSubTasks(new SubTask(3, "back", "back", Status.NEW));
        manager.createEpic(epic2);
        Epic epic3 = new Epic(3, "обновление услуги", "обновление услуги", Status.NEW);
        manager.createEpic(epic3);
        System.out.println(manager.getTask(1));
        System.out.println(manager.getSubTask(4));
        System.out.println(manager.getEpic(3));

            //создаю такого же менеджера только уже из существующего файла
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(Paths.get("resource", "\\tasks.csv"));
        System.out.println(fileBackedTasksManager.getHistory());
        System.out.println(fileBackedTasksManager.getAllEpic());
        System.out.println(fileBackedTasksManager.getHistory());

        //сравнение всех эпиков
        System.out.println(manager.getAllEpic().equals(fileBackedTasksManager.getAllEpic()));
        //сравнение обычных задач
        System.out.println(manager.getAllTask().equals(fileBackedTasksManager.getAllTask()));
        //сравнение подхадач
        System.out.println(manager.getAllSubTask().equals(fileBackedTasksManager.getAllSubTask()));
        //сревнение истории
        System.out.println(manager.getHistory().equals(fileBackedTasksManager.getHistory()));


    }
}
