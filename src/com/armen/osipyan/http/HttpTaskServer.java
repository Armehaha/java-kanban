package com.armen.osipyan.http;

import static com.armen.osipyan.http.Endpoint.*;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.service.HttpTaskManager;
import com.armen.osipyan.service.Managers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final Gson gson = new Gson();
    private final HttpTaskManager httpTaskManager;

    public HttpTaskManager getHttpTaskManager() {
        return httpTaskManager;
    }

    public HttpTaskServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", this::handle);
        httpTaskManager = Managers.getDefault();

        httpServer.start();
    }

    private void handle(HttpExchange httpExchange) throws IOException {
        Endpoint endpoint = getEndpoint(httpExchange.getRequestURI().getPath() + httpExchange.getRequestURI().getQuery(), httpExchange.getRequestMethod());
        String response;
        System.out.println(endpoint);

        int statusCode = 200;
        int id = 0;

        if (httpExchange.getRequestURI().getQuery() != null && httpExchange.getRequestURI().getQuery().contains("=")) {
            int index = httpExchange.getRequestURI().getQuery().indexOf("=");
            id = Integer.parseInt(httpExchange.getRequestURI().getQuery().substring(index + 1));
        }

        switch (endpoint) {
            case GET_ALL_TASK:
                List<Task> tasks = httpTaskManager.getAllTask();
                response = gson.toJson(tasks);
                break;
            case GET_ALL_EPIC:
                List<Task> epicList = httpTaskManager.getAllEpic();
                response = gson.toJson(epicList);
                break;
            case GET_ALL_SUB_TASK:
                List<Task> subTuskList = httpTaskManager.getAllSubTask();
                response = gson.toJson(subTuskList);
                break;
            case GET_HISTORY:
                List<Task> history = httpTaskManager.getHistory();
                response = gson.toJson(history);
                break;
            case GET_TASK:
                Task task = httpTaskManager.getTask(id);
                response = gson.toJson(task);
                break;
            case GET_EPIC:
                Task epic = httpTaskManager.getEpic(id);
                response = gson.toJson(epic);
                break;
            case GET_SUB_TASK:
                Task subTusk = httpTaskManager.getSubTask(id);
                response = gson.toJson(subTusk);
                break;
            case GET_PRIORITIZED_TASK:
                List<Task> prioritizedTask = httpTaskManager.prioritizedTask();
                response = gson.toJson(prioritizedTask);
                break;
            case GET_SUB_TASK_FROM_EPIC:
                List<Task> subTasks = httpTaskManager.getSubTaskForEpic(httpTaskManager.getEpic(id));
                response = gson.toJson(subTasks);
                break;
            case DELETE_ALL_TASK:
                httpTaskManager.deleteAllTask();
                response = "Все обычные задачи удалены";
                break;
            case DELETE_ALL_EPIC:
                httpTaskManager.deleteAllEpic();
                response = "Все эпики удалены";
                break;
            case DELETE_ALL_SUB_TASK:
                httpTaskManager.deleteAllSubTask();
                response = "Все подзадачи удалены";
                break;
            case DELETE_TASK:
                httpTaskManager.deleteTask(id);
                response = "Задача с id " + id + " удалена";
                break;
            case DELETE_EPIC:
                httpTaskManager.deleteEpic(id);
                response = "Эпик с id " + id + " удалена";
                break;
            case DELETE_SUBTASK:
                httpTaskManager.deleteSubTask(id);
                response = "Подзадача с id " + id + " удалена";
                break;
            case POST_CREATE_OR_UPDATE_TASK:
                String s = new String(httpExchange.getRequestBody()
                        .readAllBytes(), StandardCharsets.UTF_8);

                Task postTask = gson.fromJson(s, Task.class);

                if (postTask.getId() == -1) {
                    httpTaskManager.createTask(postTask);
                    response = "Обычная задача создана";
                } else {
                    httpTaskManager.updateTask(postTask);
                    response = "Обычная задача обновлена";
                }
                break;
            case POST_CREATE_OR_UPDATE_EPIC:
                String sEpic = new String(httpExchange.getRequestBody()
                        .readAllBytes(), StandardCharsets.UTF_8);
                Epic postEpic = gson.fromJson(sEpic, Epic.class);
                if (postEpic.getId() == -1) {
                    httpTaskManager.createEpic(postEpic);
                    response = "Эпик создан";
                } else {
                    httpTaskManager.updateTask(postEpic);
                    response = "Эпик обновлен";
                }
                break;
            case POST_CREATE_OR_UPDATE_SUB_TASK:
                String sSub = new String(httpExchange.getRequestBody()
                        .readAllBytes(), StandardCharsets.UTF_8);
                SubTask postSubTusk = gson.fromJson(sSub, SubTask.class);
                if (postSubTusk.getId() == -1) {
                    httpTaskManager.createSubTask(postSubTusk);
                    response = "Подзадача создана";
                } else {
                    httpTaskManager.updateTask(postSubTusk);
                    response = "Подзадача обновлена";
                }
                break;
            default:
                statusCode = 404;
                response = "Произошла ошибка";
        }

        httpExchange.sendResponseHeaders(statusCode, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        httpExchange.close();

    }


    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        System.out.println(pathParts.length);
        switch (requestMethod) {
            case "GET":
                if (pathParts.length == 3) {

                    return GET_PRIORITIZED_TASK;
                } else if (pathParts.length == 4) {

                    if (pathParts[pathParts.length - 1].equals("null")) {
                        switch (pathParts[2]) {
                            case "task":
                                return GET_ALL_TASK;
                            case "epic":
                                return GET_ALL_EPIC;
                            case "subtusk":
                                return GET_ALL_SUB_TASK;
                            case "history":
                                return GET_HISTORY;
                        }
                    } else {
                        switch (pathParts[2]) {
                            case "task":
                                return GET_TASK;
                            case "epic":
                                return GET_EPIC;
                            case "subtusk":
                                return GET_SUB_TASK;
                        }
                    }
                } else if (pathParts.length == 5) {
                    if (pathParts[3].equals("epic")) {
                        return GET_SUB_TASK_FROM_EPIC;
                    }
                }
                break;
            case "DELETE":
                if (pathParts.length == 4) {

                    if (pathParts[pathParts.length - 1].equals("null")) {
                        switch (pathParts[2]) {
                            case "task":
                                return DELETE_ALL_TASK;
                            case "epic":
                                return DELETE_ALL_EPIC;
                            case "subtusk":
                                return DELETE_ALL_SUB_TASK;
                        }
                    } else {
                        switch (pathParts[2]) {
                            case "task":
                                return DELETE_TASK;
                            case "epic":
                                return DELETE_EPIC;
                            case "subtusk":
                                return DELETE_SUBTASK;
                        }
                    }
                }
                break;
            case "POST":
                if (pathParts.length == 4) {


                    switch (pathParts[2]) {
                        case "task":
                            return POST_CREATE_OR_UPDATE_TASK;
                        case "epic":
                            return POST_CREATE_OR_UPDATE_EPIC;
                        case "subtusk":
                            return POST_CREATE_OR_UPDATE_SUB_TASK;

                    }
                }

                break;
        }
        return UNKNOWN;
    }

}
