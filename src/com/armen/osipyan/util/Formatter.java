package com.armen.osipyan.util;

import com.armen.osipyan.model.*;
import com.armen.osipyan.service.HistoryManagers;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Formatter {

    public static String historyToString(HistoryManagers manager) {
        List<String> list = new ArrayList<>();
        manager.getHistory().forEach(el -> list.add(String.valueOf(el.getId())));
        return String.join(",", list);
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> list = new ArrayList<>();
        String[] historyInfo = value.split(",");
        for (String s : historyInfo) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }

    public static String toString(Task task) {
        StringBuilder info = new StringBuilder();
        info.append(task.getId());
        info.append(",");
        if (task instanceof SubTask) {
            info.append(TaskType.SUBTASK);
            info.append(",");
        } else if (task instanceof Epic) {
            info.append(TaskType.EPIC);
            info.append(",");
        } else {
            info.append(TaskType.TASK);
            info.append(",");
        }
        info.append(task.getName());
        info.append(",");
        info.append(task.getStatus());
        info.append(",");
        info.append(task.getDescription());
        if (task instanceof SubTask) {
            info.append(",");
            info.append(((SubTask) task).getEpic().getId());
        } else {
            info.append(",");
            info.append(" ");
        }
        info.append(",");
        if (task.getDuration() == null) {
            info.append("null");
        } else {
            info.append(task.getDuration().toMinutes());

        }
        info.append(",");
        if (task.getStartTime() == null) {
            info.append("null");
        } else {
            info.append(task.getStartTime().toString());

        }
        return info.toString();
    }

    public static Task fromString(String value) {
        JsonElement jsonElement = JsonParser.parseString(value);
        Gson gson = new Gson();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("epic")) {
            SubTask subtask = new SubTask(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    Status.valueOf(jsonObject.get("status").getAsString()),
                    gson.fromJson(jsonObject.get("duration").getAsJsonObject(), Duration.class),
                    gson.fromJson(jsonObject.get("startTime").getAsJsonObject(), LocalDateTime.class),
                    gson.fromJson(jsonObject.get("epic").getAsJsonObject(), Epic.class));
            return subtask;
        } else if (jsonObject.has("subTasks")) {
            Epic epic = new Epic(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    Status.valueOf(jsonObject.get("status").getAsString()),
                    gson.fromJson(jsonObject.get("duration").getAsJsonObject(), Duration.class),
                    null);
            return epic;
        } else {
            Task task = new Task(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    Status.valueOf(jsonObject.get("status").getAsString()),
                    gson.fromJson(jsonObject.get("duration").getAsJsonObject(), Duration.class),
                    gson.fromJson(jsonObject.get("startTime").getAsJsonObject(), LocalDateTime.class));
            return task;
        }
    }

    public static List<Integer> historyFromStringServer(String value) {
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
