package com.armen.osipyan.service;

import com.armen.osipyan.http.KVTaskClient;
import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.Status;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.util.Formatter;
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
    private final String keyArmen = "Armen";

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

        kvTaskClient.put(keyArmen, s.toString());
    }

    public KVTaskClient getKvTaskClient() {
        return kvTaskClient;
    }


    public static HttpTaskManager loadS(String key) {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/register");
        return manager.load(key);
    }


    private void saveTask(String[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            if (!values[i].isBlank()) {
                Task task = Formatter.fromString(values[i]);
                if (task instanceof SubTask) {
                    createSubTask((SubTask) task);
                } else if (task instanceof Epic) {
                    createEpic((Epic) task);
                } else
                    createTask(task);
            }
        }
    }

    public HttpTaskManager load(String key) {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/register");
        String taskManager = getKvTaskClient().load(key);
        String[] values = taskManager.split("\\n");
        saveTask(values);

        String history = values[values.length - 1];
        if (history.isBlank()) {
            return manager;
        }
        List<Integer> saveHistory = Formatter.historyFromStringServer(history);
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


}
