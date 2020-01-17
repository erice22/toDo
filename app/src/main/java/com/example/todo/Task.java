package com.example.todo;
import android.provider.ContactsContract;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int task_id;
    //min format:
    private float duration=0;
    private int priority=1;
    private String description;
    @ColumnInfo(name = "date_of_update")
    private Date date_of_update;

    public Task(String description, int priority, float duration, Date date_of_update){
        this.description=description;
        this.priority=priority;
        this.duration=duration;
        this.date_of_update=date_of_update;
    }

    public String getDescription(){ return this.description;}
    public int getPriority(){ return this.priority;}
    public float getDuration(){ return this.duration;}
    public int getTask_id(){ return this.task_id;}
    public Date getDate_of_update(){ return this.date_of_update;}

    public void setDescription(String desc){this.description = desc;}
    public void setPriority(int prior){this.priority = prior;}
    public void setTask_id(int id){this.task_id = id;}
    public void setDuration(float time){this.duration = time;}
    public void setDate_of_update(Date date_of_update){this.date_of_update= date_of_update;}

}

