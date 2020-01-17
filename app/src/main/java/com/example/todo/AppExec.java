package com.example.todo;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExec {
    private static final Object LOCK = new Object();
    private static AppExec sInstance;


    private final Executor diskIO;


    private AppExec(Executor diskIO) {
        this.diskIO = diskIO;


    }


    public static AppExec getInstance(){

        if (sInstance == null) {

            synchronized (LOCK){

                sInstance = new AppExec(Executors.newSingleThreadExecutor());

            }
        }

        return sInstance;

    }



    public Executor diskIO() {
        return diskIO;
    }
}
