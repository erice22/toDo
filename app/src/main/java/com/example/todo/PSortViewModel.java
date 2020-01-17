package com.example.todo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.TypeConverters;

import java.util.List;

public class PSortViewModel extends AndroidViewModel {
    private LiveData<List<Task>> taskList;


    public PSortViewModel(@NonNull Application application) {
        super(application);
        AppDataBase appDataBase = AppDataBase.getsInstance(this.getApplication());
        Log.i("View Model"," Retrieving data from database");
        taskList =  appDataBase.taskDao().sort_priority();

    }


    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }
}