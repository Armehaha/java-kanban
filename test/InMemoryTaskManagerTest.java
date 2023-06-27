import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.InMemoryTaskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void createTaskManager() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void getPriority() {
        Task task = new Task("a", "a", Status.NEW,
                Duration.ofMinutes(5), null);

        taskManager.createTask(task);
        Epic epic = new Epic("e", "e", Status.NEW
        );

        taskManager.createEpic(epic);
        SubTask subtask = new SubTask("R", "r", Status.NEW,
                Duration.ofMinutes(16), LocalDateTime.now());

        taskManager.createSubTask(epic, subtask);

        SubTask subtask1 = new SubTask("r", "r", Status.NEW,
                Duration.ofMinutes(16), LocalDateTime.now().plusHours(17));
        taskManager.createSubTask(epic, subtask1);

        assertEquals(epic.getDuration().toMinutes(), 32);
        assertEquals(taskManager.prioritizedTask().get(0), subtask, "Неверная сортировка");


    }
}