package com.wanis.assessmentdesenvolvimentoandroid.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by munirwanis on 17/12/17.
 */

public class TasksResponse implements Serializable {

    @SerializedName("tarefa")
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
