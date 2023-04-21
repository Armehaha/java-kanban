package com.armen.osipyan.service;

import com.armen.osipyan.model.Task;

import java.util.List;

public interface HistoryManagers {
    //понимаю что по тз должны в этом методе принимать Task, но в данный момент мне показалось так проще все сделать
    void add(long id);

    List<Task> getHistory();
}
