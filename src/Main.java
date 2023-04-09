import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.Manager;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.createTask(new Task("Подготовить скрипты для вывода в прод",
                "Добавьте новые данные в таблицу nsi_regions"));

        manager.createTask(new Task("добавить настройки услуги",
                "добавьте в таблицу настрйки для новой услуги"));

        System.out.println(manager.getAllTask());
        Epic epic = new Epic("Редирект с 10700 на 606204", "редирект с одной услуги на другую");
        manager.createEpic(epic);
        System.out.println(manager.getAllEpic());
        manager.createSubTask(epic, new SubTask("фронт", "фронт"));
        manager.createSubTask(epic, new SubTask("бэк", "бэк"));
        System.out.println(manager.getAllSubTask());

        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());

        Epic epic2 = new Epic("обновление услуги", "обновление услуги");
        epic2.addSubTasks(new SubTask("back", "back"));
        manager.createEpic(epic2);
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());
    }
}
