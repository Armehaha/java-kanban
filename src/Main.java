import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.Managers;
import com.armen.osipyan.service.TaskManager;

public class Main {

     public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        manager.createTask(new Task(Managers.id++,"Подготовить скрипты для вывода в прод",
                "Добавьте новые данные в таблицу nsi_regions", Status.NEW));

        manager.createTask(new Task(Managers.id++,"добавить настройки услуги",
                "добавьте в таблицу настрйки для новой услуги",Status.NEW));

        System.out.println(manager.getAllTask());
        Epic epic = new Epic(Managers.id++,"Редирект с 10700 на 606204", "редирект с одной услуги на другую",Status.NEW);
        manager.createEpic(epic);
        System.out.println(manager.getAllEpic());
        manager.createSubTask(epic, new SubTask(Managers.id++,"фронт", "фронт",Status.NEW));
        manager.createSubTask(epic, new SubTask(Managers.id++,"бэк", "бэк",Status.NEW));
        System.out.println(manager.getAllSubTask());

        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getAllEpic());

        Epic epic2 = new Epic(Managers.id++,"обновление услуги", "обновление услуги",Status.NEW);
        epic2.addSubTasks(new SubTask(Managers.id++,"back", "back",Status.NEW));
        manager.createEpic(epic2);
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());
        System.out.println(manager.getTask(1));
        System.out.println(manager.getEpic(3));
        System.out.println(manager.getSubTask(4));
        System.out.println(manager.getHistory());
    }
}
