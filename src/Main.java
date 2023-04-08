import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Task task = new Task("test","test");
        System.out.println(task);
        Epic epic = new Epic("test","test");
        System.out.println(epic);
        SubTask subTask = new SubTask("tr","reer");
        System.out.println(subTask);
        epic.addSubTasks(subTask);
        System.out.println(epic);
    }
}
