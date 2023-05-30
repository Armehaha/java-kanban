package com.armen.osipyan.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Task> subTasks;

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public Epic(int id, String name, String formulation, Status status) {
        super(id, name, formulation, status);
        subTasks = new ArrayList<>();
    }

    public void addSubTasks(SubTask subTask) {
        subTasks.add(subTask);
        subTask.setEpic(this);
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", formulation='" + this.getDescription() + '\'' +
                "subTasks=" + subTasks.toString() +
                '}';
    }
//если тут буду вызывать метод eauals subTask то происходит рекурсия и все падает, поэтому переписал сравнения руками
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        if ( subTasks.size()!=epic.subTasks.size()){
            return false;
        }

        boolean flag;
        for (int i = 0; i < subTasks.size()-1; i++) {
            if (subTasks.get(i)==epic.subTasks.get(i)) return true;
            if (subTasks.get(i) == null || subTasks.get(i).getClass() != epic.subTasks.get(i).getClass()) return false;
           flag = subTasks.get(i).getId()==epic.subTasks.get(i).getId()&&subTasks.get(i).getName().equals(epic.subTasks.get(i).getName())&&subTasks.get(i).getDescription().equals(epic.subTasks.get(i).getDescription())&&subTasks.get(i).getStatus()==epic.subTasks.get(i).getStatus();
            if (!flag){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }
}
