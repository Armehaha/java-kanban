import com.armen.osipyan.http.HttpTaskServer;
import com.armen.osipyan.http.KVServer;
import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.HttpTaskManager;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    HttpTaskServer server;
    HttpClient client;
    Gson gson;
    KVServer kvServer;
    private final String uri = "http://localhost:8080/";

    @BeforeEach
    public void createServers() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        server = new HttpTaskServer();
        client = HttpClient.newHttpClient();
        gson = new Gson();
        taskManager = server.getHttpTaskManager();
    }

    @AfterEach
    public void closeServers() {
        kvServer.stop();

    }

    @Test
    public void postNewTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        Task task = new Task(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now());
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllTask().size(), 1);
        assertEquals(taskManager.getAllTask().get(0), task);

    }


    @Test
    public void postNewSubTask() throws IOException, InterruptedException {
        URI url2 = URI.create(uri + "tasks/subtusk/");
        SubTask subTask = new SubTask(1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now());
        String json = gson.toJson(subTask);
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url2).POST(body2).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());


        assertEquals(taskManager.getAllSubTask().size(), 1);
        assertEquals(taskManager.getAllSubTask().get(0), subTask);


    }

    @Test
    public void postNewEpic() throws IOException, InterruptedException {
        URI url2 = URI.create(uri + "tasks/epic/");
        Epic epic = new Epic(-1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), null);
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url2).POST(body2).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());


        assertEquals(taskManager.getAllEpic().size(), 1);
        System.out.println(taskManager.getAllEpic().get(0).getDescription());
        assertEquals(taskManager.getAllEpic().get(0), epic);


    }

    @Test
    public void deleteAllTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new Task(-1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Task(2, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());


        HttpRequest request2 = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllTask().size(), 0, "Задачи не удалились");

    }

    @Test
    public void deleteAllEpic() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/epic/");
        String json = gson.toJson(new Epic(-1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Epic(2, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());


        HttpRequest request2 = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllEpic().size(), 0, "Задачи не удалились");

    }

    @Test
    public void deleteAllSubTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/subtask/");
        String json = gson.toJson(new SubTask(-1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new SubTask(2, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());


        HttpRequest request2 = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllSubTask().size(), 0, "Задачи не удалились");

    }

    @Test
    public void getTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new Task(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/task/?id=0");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());


        assertEquals(response2.body(), gson.toJson(taskManager.getTask(0)), "Не верный возврат задачи");


    }

    @Test
    public void getEpic() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/epic/");
        String json = gson.toJson(new Epic(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/epic/?id=0");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());


        assertEquals(response2.body(), gson.toJson(taskManager.getEpic(0)), "Не верный возврат задачи");


    }

    @Test
    public void getSub() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/subtusk/");
        String json = gson.toJson(new SubTask(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/subtusk/?id=0");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());


        assertEquals(response2.body(), gson.toJson(taskManager.getSubTask(0)), "Не верный возврат задачи");


    }

    @Test
    public void deleteTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new Task(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url).POST(body4).build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/task/?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllTask().size(), 0, "Задачи не удалились");


    }

    @Test
    public void deleteEpic() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/epic/");
        String json = gson.toJson(new Epic(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url).POST(body4).build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/epic/?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllEpic().size(), 0, "Задачи не удалились");


    }

    @Test
    public void deleteSub() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/subtusk/");
        String json = gson.toJson(new SubTask(0, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url).POST(body4).build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/subtusk/?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllSubTask().size(), 0, "Задачи не удалились");


    }

    @Test
    public void updateTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new SubTask(-1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/task/");
        String json1 = gson.toJson(new SubTask(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request8 = HttpRequest.newBuilder().uri(url).POST(body8).build();
        client.send(request8, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllTask().size(), 1, "Неверное обновление задачи");

    }

    @Test
    public void updateEpic() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/epic/");
        String json = gson.toJson(new Epic(-1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/epic/");
        String json1 = gson.toJson(new Epic(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request8 = HttpRequest.newBuilder().uri(url).POST(body8).build();
        client.send(request8, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllEpic().size(), 1, "Неверное обновление задачи");

    }


    @Test
    public void updateSub() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/subtusk/");
        String json = gson.toJson(new SubTask(1, "t", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/subtusk/");
        String json1 = gson.toJson(new SubTask(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request8 = HttpRequest.newBuilder().uri(url).POST(body8).build();
        client.send(request8, HttpResponse.BodyHandlers.ofString());

        assertEquals(taskManager.getAllSubTask().size(), 1, "Неверное обновление задачи");

    }

    @Test
    public void getAllTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new Task(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Task(2, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());


        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(response2.body(), gson.toJson(taskManager.getAllTask()), "Задачи не совпадают");
    }

    @Test
    public void getAllEpic() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/epic/");
        String json = gson.toJson(new Epic(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Epic(2, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());


        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(response2.body(), gson.toJson(taskManager.getAllEpic()), "Задачи не совпадают");
    }

    @Test
    public void getAllSubTask() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/subtusk/");
        String json = gson.toJson(new SubTask(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new SubTask(2, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(body1).build();
        client.send(request1, HttpResponse.BodyHandlers.ofString());


        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(response2.body(), gson.toJson(taskManager.getAllSubTask()), "Задачи не совпадают");
    }

    @Test
    public void getHistory() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new Task(2, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Task(3, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request8 = HttpRequest.newBuilder().uri(url).POST(body8).build();
        client.send(request8, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Epic(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url).POST(body4).build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/epic/");
        json = gson.toJson(new Epic(5, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body9 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request9 = HttpRequest.newBuilder().uri(url).POST(body9).build();
        client.send(request9, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/subtusk/");
        json = gson.toJson(new SubTask(6, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body5 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request5 = HttpRequest.newBuilder().uri(url).POST(body5).build();
        client.send(request5, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/subtusk/?id=6");
        HttpRequest request3 = HttpRequest.newBuilder().uri(url).GET().build();
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/epic/?id=5");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        client.send(request2, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/task/?id=3");
        HttpRequest request10 = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request10, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/history/");
        HttpRequest request11 = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response11 = client.send(request11, HttpResponse.BodyHandlers.ofString());

        assertEquals(response11.body(), gson.toJson(taskManager.getHistory()));
    }


    @Test
    public void saveAndLoadHttpTaskManagerNormal() throws IOException, InterruptedException {
        URI url = URI.create(uri + "tasks/task/");
        String json = gson.toJson(new Task(2, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Task(3, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request8 = HttpRequest.newBuilder().uri(url).POST(body8).build();
        client.send(request8, HttpResponse.BodyHandlers.ofString());

        json = gson.toJson(new Epic(1, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url).POST(body4).build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/epic/");
        json = gson.toJson(new Epic(5, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body9 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request9 = HttpRequest.newBuilder().uri(url).POST(body9).build();
        client.send(request9, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/subtusk/");
        json = gson.toJson(new SubTask(6, "tt", "t", Status.NEW,
                Duration.ofMinutes(5), LocalDateTime.now()));
        final HttpRequest.BodyPublisher body5 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request5 = HttpRequest.newBuilder().uri(url).POST(body5).build();
        client.send(request5, HttpResponse.BodyHandlers.ofString());


        url = URI.create(uri + "tasks/subtusk/?id=6");
        HttpRequest request3 = HttpRequest.newBuilder().uri(url).GET().build();
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/epic/?id=5");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).GET().build();
        client.send(request2, HttpResponse.BodyHandlers.ofString());

        url = URI.create(uri + "tasks/task/?id=3");
        HttpRequest request10 = HttpRequest.newBuilder().uri(url).DELETE().build();
        client.send(request10, HttpResponse.BodyHandlers.ofString());


        HttpTaskManager newManager = HttpTaskManager.loadS("Armen");
        assertEquals(newManager, taskManager);
    }
}
