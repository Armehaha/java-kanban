package com.armen.osipyan.model;

public class SubTask extends Task {
    private Epic epic;

    public Epic getEpic() {
        return epic;
    }


    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public SubTask(long id, String name, String formulation, Status status) {
        super(id, name, formulation, status);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", formulation='" + this.getFormulation() + '\'' +
                "epic=" + epic.getName() +
                '}';
    }
}
