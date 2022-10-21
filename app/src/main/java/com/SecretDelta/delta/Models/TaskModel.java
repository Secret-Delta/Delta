package com.SecretDelta.delta.Models;


import android.widget.Spinner;

import java.util.ArrayList;

public class TaskModel {
    private int id;
    private String task, description, priority;
    private ArrayList<SubTaskModel> subTasks;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ArrayList<SubTaskModel> getArrayList() {
        return subTasks;
    }

    public void setArrayList(ArrayList<SubTaskModel> subTasks) {
        this.subTasks = subTasks;
    }
}
