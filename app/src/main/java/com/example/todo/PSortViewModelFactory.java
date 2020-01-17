package com.example.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.app.Application;

public class PSortViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private Application app;
        public PSortViewModelFactory(Application appl) {
            app = appl;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PSortViewModel(app);
        }
}
