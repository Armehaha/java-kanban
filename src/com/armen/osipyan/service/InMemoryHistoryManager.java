package com.armen.osipyan.service;

import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.List;

import static com.armen.osipyan.service.Managers.*;

public class InMemoryHistoryManager implements HistoryManagers {
    @Override
    public void add(long id) {
        if (!(historyTask.size() >= 10)) {
            historyTask.add(id);
        } else {
            historyTask.remove(0);
            historyTask.add(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        for (Long l : historyTask) {
            if (longSubTaskHashMap.get(l) != null) {
                list.add(longSubTaskHashMap.get(l));
            } else if (longEpicHashMap.get(l) != null) {
                list.add(longEpicHashMap.get(l));
            } else
                list.add(longTaskHashMap.get(l));


        }
        return list;
    }
}
