package com.armen.osipyan.model;

public class SubTask extends Task{
 private  Epic epic;
    @Override
    public String toString() {
        return super.toString();
    }

    public Epic getEpic() {
        return epic;
    }

    public SubTask(String name, String formulation, Status status) {
        super(name, formulation, status);
    }

    public SubTask(String name, String formulation) {
        super(name, formulation);
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
