package com.example.todo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDataBase appDataBase;
    private final int id;

    public AddTaskViewModelFactory(AppDataBase db, int taskId) {

        appDataBase = db;
        id = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(appDataBase,id);
    }
}
