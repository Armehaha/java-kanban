import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.service.FileBackedTasksManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import com.armen.osipyan.model.Task;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    public void createFileBacked() throws FileNotFoundException {
        taskManager = new FileBackedTasksManager(Paths.get("resource", "\\tasks.csv"));
    }

    @AfterAll
    public static void delete() throws IOException {
        Files.delete(Paths.get("resource", "\\tasks.csv"));
    }

    @Test
    public void createFileBackedFromEmptyHistory() {
        Task simpleTask = new Task("test", "test1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(simpleTask);
        taskManager.deleteTask(simpleTask.getId());
        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(Paths.get("resource", "\\tasks.csv"));
        assertNull(newManager, "Менеджер создался");

    }

    @Test
    public void createFileBackedHistory() {
        Task simpleTask = new Task("test", "test1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(simpleTask);
        Epic epicTask = new Epic("epic", "epic1", Status.NEW);

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("subTask", "subTask1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createSubTask(epicTask, subtask);
        taskManager.getEpic(epicTask.getId());
        taskManager.getTask(simpleTask.getId());
        taskManager.getSubTask(subtask.getId());
        FileBackedTasksManager newTaskManager = FileBackedTasksManager.loadFromFile(Paths.get("resource", "\\tasks.csv"));

        assertNotNull(newTaskManager, "Файлы не сохранились");

    }

    @Test
    public void createFileBackedHistoryFromEpic() {
        Epic epicTask = new Epic("epic", "epic1", Status.NEW);

        taskManager.createEpic(epicTask);
        taskManager.getEpic(epicTask.getId());
        FileBackedTasksManager newTaskManager = FileBackedTasksManager.loadFromFile(Paths.get("resource", "\\tasks.csv"));

        assertNotNull(newTaskManager, "Файлы не сохранились");


    }



}
