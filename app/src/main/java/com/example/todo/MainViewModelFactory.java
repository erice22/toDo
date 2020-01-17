package com.example.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application app;
    public MainViewModelFactory(Application appl) {
        app = appl;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(app);
    }
}
