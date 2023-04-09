import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.model.Manager;

public class Main {

    public static void main(String[] args) {
//        Task task = new Task("test","test");
//        System.out.println(task);
//        Epic epic = new Epic("test","test");
//        System.out.println(epic);
//        SubTask subTask = new SubTask("tr","reer");
//        System.out.println(subTask);
//        epic.addSubTasks(subTask);
//        System.out.println(epic);
        Manager manager = new Manager();
        manager.createTask(new Task("Создать скрипты","описать скрипты"));
        Epic epic = new Epic("разработка редиректа","создание нового функционала");
        manager.createEpic(epic);
        manager.createSubTask(epic,new SubTask("front","fro"));
        manager.createSubTask(epic,new SubTask("back","back"));
        System.out.println(Manager.longEpicHashMap);
        System.out.println(Manager.longTaskHashMap);
        System.out.println(Manager.longSubTaskHashMap);
        manager.deleteSubTask(3);
        System.out.println(Manager.longSubTaskHashMap);
        manager.deleteTask(10);
        System.out.println(Manager.longEpicHashMap.get(2L).getSubTasks());

    }
}
