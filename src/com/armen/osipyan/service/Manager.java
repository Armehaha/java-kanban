package com.armen.osipyan.service;

import com.armen.osipyan.model.Epic;
import com.armen.osipyan.model.SubTask;
import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    public static long id;
    public static HashMap<Integer, Task> integerTaskHashMap;
    public static HashMap<Integer, Epic> integerEpicHashMap;
    public static HashMap<Integer, ArrayList<SubTask>> integerArrayListHashMap;


}
