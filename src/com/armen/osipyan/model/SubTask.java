package com.armen.osipyan.model;

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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
