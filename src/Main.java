import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.Managers;
import com.armen.osipyan.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        manager.createTask(new Task(1, "Подготовить скрипты для вывода в прод",
                "Добавьте новые данные в таблицу nsi_regions", Status.NEW));

        manager.createTask(new Task(1, "добавить настройки услуги",
                "добавьте в таблицу настрйки для новой услуги", Status.NEW));

        System.out.println(manager.getAllTask());
        Epic epic = new Epic(1, "Редирект с 10700 на 606204", "редирект с одной услуги на другую", Status.NEW);
        manager.createEpic(epic);
//        System.out.println(manager.getAllEpic());
        manager.createSubTask(epic, new SubTask(1, "фронт", "фронт", Status.NEW));
        manager.createSubTask(epic, new SubTask(2, "бэк", "бэк", Status.NEW));
        System.out.println(manager.getAllSubTask());

//        System.out.println(manager.getAllSubTask());
//        System.out.println(manager.getAllEpic());

        Epic epic2 = new Epic(3, "обновление услуги", "обновление услуги", Status.NEW);
        epic2.addSubTasks(new SubTask(3, "back", "back", Status.NEW));
        manager.createEpic(epic2);
        Epic epic3 = new Epic(3, "обновление услуги", "обновление услуги", Status.NEW);
        manager.createEpic(epic3);
//        System.out.println(manager.getAllEpic());
//        System.out.println(manager.getAllSubTask());
//        System.out.println(manager.getTask(1));
//        System.out.println(manager.getEpic(3));
//        System.out.println(manager.getSubTask(4));
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getHistory());
    }
}
