package com.armen.osipyan.service;

import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.List;

import static com.armen.osipyan.service.Managers.*;

public class InMemoryHistoryManager implements HistoryManagers {
    protected static final List<Task> historyTask = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (historyTask.size()<10) {
            historyTask.add(task);
        } else {
            historyTask.remove(0);
            historyTask.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<Task>(historyTask);
    }
}
