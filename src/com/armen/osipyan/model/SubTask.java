package com.armen.osipyan.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {
    private Epic epic;

    public Epic getEpic() {
        return epic;
    }


    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public SubTask(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);

    }

    public SubTask(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime, Epic epic) {
        super(id, name, description, status, duration, startTime);
        this.epic = epic;
    }

    public SubTask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
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
