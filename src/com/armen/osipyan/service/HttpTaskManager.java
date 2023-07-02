package com.armen.osipyan.service;

import com.armen.osipyan.http.KVTaskClient;
import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.google.gson.*;


import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.armen.osipyan.util.Formatter.historyToString;

public class HttpTaskManager extends FileBackedTasksManager {

    private final Gson gson;

    private final KVTaskClient kvTaskClient;


    public HttpTaskManager(String uri) {

        kvTaskClient = new KVTaskClient(URI.create(uri));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.serializeNulls().create();

    }

    @Override
    protected void save() {
        StringBuilder s = new StringBuilder();
        for (Task task : longTaskHashMap.values()) {
            s.append(gson.toJson(task)).append("\n");
        }
        for (Epic epic : longEpicHashMap.values()) {
            s.append(gson.toJson(epic)).append("\n");
        }
        for (SubTask subTask : longSubTaskHashMap.values()) {
            s.append(gson.toJson(subTask)).append("\n");
        }
        s.append("\n ").append(historyToString(historyManagers));
        String key = "Armen";
        kvTaskClient.put(key, s.toString());
    }

    public KVTaskClient getKvTaskClient() {
        return kvTaskClient;
    }

    private Task fromString(String value) {
        JsonElement jsonElement = JsonParser.parseString(value);

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("epic")) {
            SubTask subtask = new SubTask(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    Status.valueOf(jsonObject.get("status").getAsString()),
                    gson.fromJson(jsonObject.get("duration").getAsJsonObject(), Duration.class),
                    gson.fromJson(jsonObject.get("startTime").getAsJsonObject(), LocalDateTime.class),
                    gson.fromJson(jsonObject.get("epic").getAsJsonObject(), Epic.class));
            createSubTask(subtask.getEpic(), subtask);
            return subtask;
        } else if (jsonObject.has("subTasks")) {
            Epic epic = new Epic(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    Status.valueOf(jsonObject.get("status").getAsString()),
                    gson.fromJson(jsonObject.get("duration").getAsJsonObject(), Duration.class),
                    null);
            createEpic(epic);
            return epic;
        } else {
            Task task = new Task(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    Status.valueOf(jsonObject.get("status").getAsString()),
                    gson.fromJson(jsonObject.get("duration").getAsJsonObject(), Duration.class),
                    gson.fromJson(jsonObject.get("startTime").getAsJsonObject(), LocalDateTime.class));
            createTask(task);
            return task;
        }
    }

    public static HttpTaskManager loadS(String key) {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/register");
        return manager.load(key);
    }

    public HttpTaskManager load(String key) {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/register");
        String taskManager = getKvTaskClient().load(key);
        String[] values = taskManager.split("\\n");

        for (int i = 0; i < values.length - 1; i++) {
            if (!values[i].isBlank()) {
                Task task = fromString(values[i]);
            }
        }
        String history = values[values.length - 1];
        if (history.isBlank()) {
            return manager;
        }
        List<Integer> saveHistory = historyFromString(history);
        for (Integer integer : saveHistory) {

            if (longTaskHashMap.containsKey(integer)) {
                historyManagers.add(longTaskHashMap.get(integer));
            } else if (longEpicHashMap.containsKey(integer)) {
                historyManagers.add(longEpicHashMap.get(integer));
            } else if (longSubTaskHashMap.containsKey(integer)) {
                historyManagers.add(longSubTaskHashMap.get(integer));
            }
        }
        return manager;
    }

    private static List<Integer> historyFromString(String value) {
        String[] arrayId = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String s : arrayId) {
            if (s.isBlank()) {
                return history;
            }
            if (s.length() > 1) {
                s = s.substring(1);
            }
            history.add(Integer.parseInt(s));
        }
        return history;
    }


}
