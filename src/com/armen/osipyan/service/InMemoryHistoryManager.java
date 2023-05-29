package com.armen.osipyan.service;

import com.armen.osipyan.model.Node;
import com.armen.osipyan.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManagers {
    protected final CustomLinkedList historyTask = new CustomLinkedList();
    protected final Map<Integer, Node<Task>> tasks = new HashMap<>();

    @Override
    public void add(Task task) {

        if (tasks.containsKey(task.getId())) {
            historyTask.removeNode(tasks.get(task.getId()));
            tasks.remove(task.getId());

        }
        historyTask.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (tasks.containsKey(id)) {
            historyTask.removeNode(tasks.get(id));
            tasks.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTask.getTasks();
    }

    class CustomLinkedList {
        private Node<Task> first;
        private Node<Task> last;

        void linkLast(Task e) {
            final Node<Task> l = last;
            final Node<Task> newNode = new Node<>(l, e, null);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.setNext(newNode);
            tasks.put(e.getId(), newNode);
        }

        public List<Task> getTasks() {
            final List<Task> taskList = new ArrayList<>();
            Node<Task> node = first;
            while (node != null) {
                taskList.add(node.getItem());
                node = node.getNext();
            }
            return taskList;
        }

        void removeNode(Node<Task> node) {
            final Node<Task> next = node.getNext();
            final Node<Task> prev = node.getPrev();

            if (prev == null) {
                first = next;
            } else {
                prev.setNext(next);
                node.setPrev(null);
            }

            if (next == null) {
                last = prev;
            } else {
                next.setPrev(prev);
                node.setNext(null);
            }
            node.setItem(null);


        }
    }
}
