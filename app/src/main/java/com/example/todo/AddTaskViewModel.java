package com.example.todo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private LiveData<Task> task;

    public AddTaskViewModel(AppDataBase appDataBase, int id) {

        task = appDataBase.taskDao().getTaskById(id);
        Log.i(" Add Task View Model "," Loading a task");

    }

    public LiveData<Task> getTask() {

        return task;
    }

}
