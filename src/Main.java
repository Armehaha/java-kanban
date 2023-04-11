import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.Manager;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.createTask(new Task(Manager.id++,"Подготовить скрипты для вывода в прод",
                "Добавьте новые данные в таблицу nsi_regions", Status.NEW));

        manager.createTask(new Task(Manager.id++,"добавить настройки услуги",
                "добавьте в таблицу настрйки для новой услуги",Status.NEW));

        System.out.println(manager.getAllTask());
        Epic epic = new Epic(Manager.id++,"Редирект с 10700 на 606204", "редирект с одной услуги на другую",Status.NEW);
        manager.createEpic(epic);
        System.out.println(manager.getAllEpic());
        manager.createSubTask(epic, new SubTask(Manager.id++,"фронт", "фронт",Status.NEW));
        manager.createSubTask(epic, new SubTask(Manager.id++,"бэк", "бэк",Status.NEW));
        System.out.println(manager.getAllSubTask());

        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());

        Epic epic2 = new Epic(Manager.id++,"обновление услуги", "обновление услуги",Status.NEW);
        epic2.addSubTasks(new SubTask(Manager.id++,"back", "back",Status.NEW));
        manager.createEpic(epic2);
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getTask(1));
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getSubTask(4));
    }
}
