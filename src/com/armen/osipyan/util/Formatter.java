package com.armen.osipyan.util;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;
import com.armen.osipyan.model.TaskType;
import com.armen.osipyan.service.HistoryManagers;

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
        }else {
            info.append(",");
            info.append(" ");
        }
        info.append(",");
        if (task.getDuration() == null) {
            info.append("null");
        }else {
            info.append(task.getDuration().toMinutes());

        }
        info.append(",");
        if (task.getStartTime()==null){
            info.append("null");
        } else {
            info.append(task.getStartTime().toString());

        }
        return info.toString();
    }
}
