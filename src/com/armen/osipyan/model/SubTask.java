package com.armen.osipyan.model;

import java.util.Objects;

public class SubTask extends Task {
    private Epic epic;

    public Epic getEpic() {
        return epic;
    }


    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public SubTask(int id, String name, String formulation, Status status) {
        super(id, name, formulation, status);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", formulation='" + this.getDescription() + '\'' +
                "epic=" + epic.getName() +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        SubTask subTask = (SubTask) o;
//        if (epic == subTask.epic) return true;
//        if (subTask.epic == null || epic.getClass() != subTask.epic.getClass()) return false;
//        if (!epic.equals(subTask.epic)) return false;
//        return epic.getId() ==subTask.epic.getId()&&epic.getName().equals(subTask.epic.getName())&&epic.getDescription().equals(subTask.epic.getDescription())&& epic.getStatus()==subTask.getStatus();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epic, subTask.epic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epic);
    }
}
