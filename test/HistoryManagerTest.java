
import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.HistoryManagers;
import com.armen.osipyan.service.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    HistoryManagers historyManager;
    Task task;
    Epic epic;
    SubTask subTask;


    @BeforeEach
    public void create() {
        historyManager = Managers.getDefaultHistory();
        task = new Task(1, "test", "test1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );
        epic = new Epic("epic1", "epic1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );
        subTask = new SubTask(2, "subtask1", "subtask1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now(), epic
        );

    }

    @Test
    public void addHistory() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История содержит лишнюю информацию");
        assertEquals(1, history.size(), "Количество эллементов не совпадают с реальной историей");
    }

    @Test
    public void getHistoryFromEmptyHistory() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не null");
        assertEquals(history.size(), 0, "Количество эллементов не совпадают с реальной историей");
    }

    @Test
    public void addHistory2tasks() {
        historyManager.add(task);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История содержит лишнюю информацию");
        assertEquals(1, history.size(), "Количество эллементов не совпадают с реальной историей");
    }

    @Test
    public void addHistoryAndDelete1Task() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);

        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История содержит лишнюю информацию");
        assertEquals(history.size(), 2, "Количество эллементов не совпадают с реальной историей");
    }

    @Test
    public void addHistoryAndDeleteMiddleTask() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);

        historyManager.remove(subTask.getId());
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История содержит лишнюю информацию");
        assertEquals(history.size(), 2, "Количество эллементов не совпадают с реальной историей");
    }

    @Test
    public void addHistoryAndDeleteEndTask() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);


        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История содержит лишнюю информацию");
        assertEquals(history.size(), 3, "Количество эллементов не совпадают с реальной историей");
    }


    @Test
    public void deleteHistory() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не существует");
        assertEquals(history.size(), 0, "Количество эллементов не совпадают с реальной историей");
    }

    @Test
    public void deleteHistoryDuplication() {
        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(epic);
        historyManager.remove(task.getId());
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История содержит лишнюю информацию");
        assertEquals(1, history.size(), "Количество эллементов не совпадают с реальной историей");
    }


}
