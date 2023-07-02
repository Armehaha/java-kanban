package com.armen.osipyan.service;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class Managers {

    public static HttpTaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8078/register");
    }

    public static TaskManager getFileBackedDefault() throws FileNotFoundException {
        return new FileBackedTasksManager(Paths.get("resource", "\\tasks.csv"));
    }


    public static HistoryManagers getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
