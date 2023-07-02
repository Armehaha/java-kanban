import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @Test
    public void checkEpicStatus() {
        Epic epicTask = new Epic("Test", "Test", Status.NEW);

        taskManager.createEpic(epicTask);
        assertEquals(epicTask.getStatus(), Status.NEW, "Статус задачи отличается от NEW");
        SubTask subtask = new SubTask("SubTest", "Sub", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createSubTask(epicTask, subtask);
        assertEquals(epicTask.getStatus(), subtask.getStatus(),
                "Статус Эпика отличается от статуса подзадачи");
        SubTask subtask1 = new SubTask("Sub1", "Sub1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createSubTask(epicTask, subtask1);
        assertEquals(epicTask.getStatus(), Status.NEW, "Статус Эпика отличается от NEW");
        taskManager.updateTask(new SubTask(subtask.getId(), "sub", "sub1",
                Status.IN_PROGRESS, Duration.ofMinutes(5), LocalDateTime.now(), subtask.getEpic()));
        taskManager.updateTask(new SubTask(subtask1.getId(), "sub2", "sub2",
                Status.IN_PROGRESS, Duration.ofMinutes(5), LocalDateTime.now(), subtask1.getEpic()));
        assertEquals(epicTask.getStatus(), Status.IN_PROGRESS, "Статус Эпика отличается от IN_PROGRESS");

        taskManager.updateTask(new SubTask(subtask.getId(), "sub", "sub1",
                Status.DONE, Duration.ofMinutes(5), LocalDateTime.now(), subtask.getEpic()));
        taskManager.updateTask(new SubTask(subtask1.getId(), "sub2", "sub2",
                Status.DONE, Duration.ofMinutes(5), LocalDateTime.now(), subtask1.getEpic()));
        assertEquals(epicTask.getStatus(), Status.DONE, "Статус Эпика отличается от DONE");

    }

    @Test
    public void addNewTask() {
        Task simpleTask = new Task("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(simpleTask);
        final Task taskTest = taskManager.getTask(simpleTask.getId());

        assertNotNull(taskTest, "Задача не найдена");
        assertEquals(simpleTask, taskTest, "Задачи не совпадают");
        final List<Task> simpleTaskList = taskManager.getAllTask();

        assertNotNull(simpleTaskList, "Список простых задач не найден");
        assertEquals(1, simpleTaskList.size(), "Неверное количество задач");
        assertEquals(simpleTask, simpleTaskList.get(0), "Задачи не совпадают");
    }

    @Test
    public void addNewEpic() {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        final Epic taskTest = taskManager.getEpic(epicTask.getId());

        assertNotNull(taskTest, "Задача не найдена");
        assertEquals(epicTask, taskTest, "Задачи не совпадают");
        final List<Task> epicTaskList = taskManager.getAllEpic();

        assertNotNull(epicTaskList, "Список Эпик задач не найден");
        assertEquals(1, epicTaskList.size(), "Неверное количество задач");
        assertEquals(epicTask, epicTaskList.get(0), "Задачи не совпадают");
        assertEquals(epicTask.getStatus(), Status.NEW, "Статус пустого эпика не совпадает с NEW");
    }

    @Test
    public void addNewSubTask() {
        Epic epicTask = new Epic("task", "task", Status.NEW
        );

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );

        taskManager.createSubTask(epicTask, subtask);
        final SubTask subTest = taskManager.getSubTask(subtask.getId());

        assertEquals(subtask.getEpic(), epicTask, "Задача не в списке Эпика");
        assertNotNull(subTest, "Задача не найдена");
        assertEquals(subtask, subTest, "Задачи не совпадают");
        final List<Task> subTaskList = taskManager.getAllSubTask();

        assertNotNull(subTaskList, "Список подзадач не найден");
        assertEquals(1, subTaskList.size(), "Неверное количество задач");
        assertEquals(subtask, subTaskList.get(0), "Задачи не совпадают");
        assertEquals(epicTask.getStatus(), Status.NEW, "Неверно рассчитан статус Эпика");
        assertEquals(epicTask, subtask.getEpic(), "Неверный epicID у Сабтаски");
    }

    @Test
    public void getAllTask() throws IOException, InterruptedException {
        Task simpleTask = new Task("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );

        taskManager.createTask(simpleTask);
        final Task taskTest = taskManager.getTask(simpleTask.getId());

        assertNotNull(taskTest, "Задача не найдена");
        assertEquals(simpleTask, taskTest, "Задачи не совпадают");
        final List<Task> listSimple = taskManager.getAllTask();

        assertNotNull(listSimple, "Список простых задач не найден");
        assertEquals(1, listSimple.size(), "Неверное количество задач");
        assertEquals(simpleTask, listSimple.get(0), "Задачи не совпадают");
    }

    @Test
    public void getAllEpic() throws IOException, InterruptedException {
        Epic epicTask = new Epic("task", "task", Status.NEW
        );

        taskManager.createEpic(epicTask);
        final Epic taskTest = taskManager.getEpic(epicTask.getId());

        assertNotNull(taskTest, "Задача не найдена");
        assertEquals(epicTask, taskTest, "Задачи не совпадают");
        final List<Task> listEpic = taskManager.getAllEpic();

        assertNotNull(listEpic, "Список эпик задач не найден");
        assertEquals(1, listEpic.size(), "Неверное количество задач");
        assertEquals(epicTask, listEpic.get(0), "Задачи не совпадают");
    }

    @Test
    public void getAllSubTask() throws IOException, InterruptedException {
        Epic epicTask = new Epic("task", "task", Status.NEW
        );

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );

        taskManager.createSubTask(epicTask, subtask);
        final SubTask newSub = taskManager.getSubTask(subtask.getId());

        assertNotNull(newSub, "Задача не найдена");
        assertEquals(newSub, subtask, "Задачи не совпадают");
        assertEquals(subtask.getEpic(), epicTask, "Задача не в списке Эпика");
        assertEquals(epicTask.getStatus(), Status.NEW, "Неверно рассчитан статус Эпика");
        List<Task> listSub = taskManager.getAllSubTask();
        assertNotNull(listSub, "Список подзадач не найден");
        assertEquals(1, listSub.size(), "Неверное количество задач");
        assertEquals(subtask, listSub.get(0), "Задачи не совпадают");
    }

    @Test
    public void getTaskById() {
        Task simpleTask = new Task("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(simpleTask);
        final Task newSimple = taskManager.getTask(simpleTask.getId());


        assertNotNull(newSimple, "Задача не найдена");
        assertEquals(newSimple, simpleTask, "Задачи не совпадают");
        final List<Task> listSimple = taskManager.getAllTask();

        assertNotNull(listSimple, "Список простых задач не найден");
        assertEquals(1, listSimple.size(), "Неверное количество задач");
        assertEquals(simpleTask, listSimple.get(0), "Задачи не совпадают");
    }

    @Test
    public void getEpicById() {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        final Epic newEpic = taskManager.getEpic(epicTask.getId());


        assertNotNull(newEpic, "Задача не найдена");
        assertEquals(epicTask, newEpic, "Задачи не совпадают");
        final List<Task> listEpic = taskManager.getAllEpic();

        assertNotNull(listEpic, "Список эпик задач не найден");
        assertEquals(1, listEpic.size(), "Неверное количество задач");
        assertEquals(epicTask, listEpic.get(0), "Задачи не совпадают");

    }

    @Test
    public void getSubtaskById() {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createSubTask(epicTask, subtask);

        assertEquals(subtask.getId(), epicTask.getSubTasks().get(0).getId(), "Задача не в списке Эпика");
        assertEquals(epicTask.getStatus(), Status.NEW, "Неверно рассчитан статус Эпика");
        final SubTask newSub = taskManager.getSubTask(subtask.getId());

        assertNotNull(newSub, "Задача не найдена");
        assertEquals(newSub, subtask, "Задачи не совпадают");
        final List<Task> listSub = taskManager.getAllSubTask();

        assertNotNull(listSub, "Список подзадач не найден");
        assertEquals(1, listSub.size(), "Неверное количество задач");
        assertEquals(subtask, listSub.get(0), "Задачи не совпадают");

    }

    @Test
    public void deleteAllTask() throws IOException, InterruptedException {
        Task simpleTask = new Task("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(simpleTask);
        taskManager.deleteAllTask();

        assertEquals(taskManager.getAllTask().size(), 0, "Мапа простых задач не очищен");
    }

    @Test
    public void deleteAllEpic() throws IOException, InterruptedException {
        Epic epicTask = new Epic("task", "task", Status.NEW
        );

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );

        taskManager.createSubTask(epicTask, subtask);
        taskManager.deleteAllEpic();
        assertEquals(taskManager.getAllEpic().size(), 0, "Список эпик не был очищена");
        assertEquals(taskManager.getAllSubTask().size(), 0, "Список подзадач не был очищена");
    }

    @Test
    public void deleteAllSubTask() throws IOException, InterruptedException {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createSubTask(epicTask, subtask);
        taskManager.deleteAllSubTask();
        assertEquals(epicTask.getSubTasks().size(), 0, "Список подзадач в эпике не очищен");
        assertEquals(epicTask.getStatus(), Status.NEW, "Неверно рассчитан статус эпика");
        assertEquals(taskManager.getAllSubTask().size(), 0, "Список подзадач не был очищена");
    }

    @Test
    public void deleteTask() throws IOException, InterruptedException {
        Task simpleTask = new Task("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(simpleTask);
        taskManager.deleteTask(simpleTask.getId());

        assertEquals(taskManager.getAllTask().size(), 0, "Мапа простых задач не очищен");
    }

    @Test
    public void deleteEpic() throws IOException, InterruptedException {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createSubTask(epicTask, subtask);
        taskManager.deleteEpic(epicTask.getId());


        assertEquals(taskManager.getAllEpic().size(), 0, "Список эпик не был очищена");
        assertEquals(taskManager.getAllSubTask().size(), 0, "Список подзадач не был очищена");
    }

    @Test
    public void deleteSubTask() {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        SubTask subtask = new SubTask("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now()
        );

        taskManager.createSubTask(epicTask, subtask);
        taskManager.deleteSubTask(subtask.getId());

        assertEquals(epicTask.getSubTasks().size(), 0, "Список подзадач в эпике не очищен");
        assertEquals(epicTask.getStatus(), Status.NEW, "Неверно рассчитан статус эпика");
        assertEquals(taskManager.getAllSubTask().size(), 0, "Мапа подзадач не был очищена");
    }

    @Test
    public void updateTask() throws IOException, InterruptedException {
        Task oldTask = new Task("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createTask(oldTask);
        Task simpleTask = new Task(oldTask.getId(), "New1", "NewDesc0",
                Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.updateTask(simpleTask);
        assertEquals(simpleTask.getId(), oldTask.getId(), "Id модификаторы не совпадают");
        assertNotEquals(simpleTask, oldTask, "Обновление задачи не произошло");
        final Task taskTest = taskManager.getTask(simpleTask.getId());

        assertNotNull(taskTest, "Задача не найдена");
        assertEquals(simpleTask, taskTest, "Задачи не совпадают");
        final List<Task> simpleTaskList = taskManager.getAllTask();

        assertNotNull(simpleTaskList, "Список простых задач не найден");
        assertEquals(1, simpleTaskList.size(), "Неверное количество задач");
        assertEquals(simpleTask, simpleTaskList.get(0), "Задачи не совпадают");
    }

    @Test
    public void updateEpic() throws IOException, InterruptedException {
        Epic oldEpic = new Epic("task", "task", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());

        taskManager.createEpic(oldEpic);
        Epic epicTask = new Epic(oldEpic.getId(), "New1", "NewDesc1",
                Status.IN_PROGRESS);

        taskManager.updateTask(epicTask);
        assertEquals(epicTask.getId(), oldEpic.getId(), "Id модификаторы не совпадают");

        assertNotEquals(epicTask, oldEpic, "Обновление эпика не произошло");
        final Epic taskTest = taskManager.getEpic(epicTask.getId());

        assertNotNull(taskTest, "Задача не найдена");
        assertEquals(epicTask, taskTest, "Задачи не совпадают");
        final List<Task> epicTaskList = taskManager.getAllEpic();

        assertNotNull(epicTaskList, "Список Эпик задач не найден");
        assertEquals(1, epicTaskList.size(), "Неверное количество задач");
        assertEquals(epicTask, epicTaskList.get(0), "Задачи не совпадают");
    }

    @Test
    public void updateSubtask() {
        Epic epicTask = new Epic("task", "task", Status.NEW);

        taskManager.createEpic(epicTask);
        SubTask oldSubTask = new SubTask("task", "task", Status.IN_PROGRESS, Duration.ofMinutes(5), LocalDateTime.now()
        );

        taskManager.createSubTask(epicTask, oldSubTask);
        SubTask subtask = new SubTask(oldSubTask.getId(), "New1", "NewDesc1",
                Status.DONE, Duration.ofMinutes(5), LocalDateTime.now(), oldSubTask.getEpic());

        taskManager.updateTask(subtask);
        assertEquals(subtask.getId(), oldSubTask.getId(), "Id модификаторы не совпадают");
        assertNotEquals(subtask, oldSubTask, "Обновление подзадач не произошло");
        assertEquals(subtask.getEpic(), oldSubTask.getEpic(), "Задачи принадлежат разным эпикам");
        final SubTask subTest = taskManager.getSubTask(subtask.getId());

        assertEquals(subtask.getId(), epicTask.getSubTasks().get(0).getId(), "Задача не в списке Эпика");
        assertNotNull(subTest, "Задача не найдена");
        assertEquals(subtask, subTest, "Задачи не совпадают");
        final List<Task> subTaskList = taskManager.getAllSubTask();

        assertNotNull(subTaskList, "Список подзадач не найден");
        assertEquals(1, subTaskList.size(), "Неверное количество задач");
        assertEquals(subtask, subTaskList.get(0), "Задачи не совпадают");
        assertEquals(epicTask.getStatus(), Status.DONE, "Неверно рассчитан статус Эпика");
        assertEquals(epicTask.getId(), subtask.getEpic().getId(), "Неверный epicID у Сабтаски");
    }


}