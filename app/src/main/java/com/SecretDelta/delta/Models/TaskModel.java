package com.SecretDelta.delta.Models;

import java.util.ArrayList;

public class TaskModel {
    private int id;
    private String task;
    private ArrayList<SubTaskModel> arrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public ArrayList<SubTaskModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<SubTaskModel> arrayList) {
        this.arrayList = arrayList;
    }

}
