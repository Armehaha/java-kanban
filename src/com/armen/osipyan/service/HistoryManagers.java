package com.armen.osipyan.service;

import com.armen.osipyan.model.Task;

import java.util.List;

public interface HistoryManagers {
    void add(Task task);

    void remove(int id);

    List<Task> getHistory();
}
