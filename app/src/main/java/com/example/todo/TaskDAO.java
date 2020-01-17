package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Dao;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task ORDER BY date_of_update")
    LiveData<List<Task>> loadTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task ORDER BY priority")
    LiveData<List<Task>> sort_priority();

    @Query("SELECT * FROM task ORDER BY duration")
    LiveData<List<Task>> sort_duration();

    @Query("SELECT * FROM task WHERE task_id= :id")
    LiveData<Task> getTaskById(int id);
}
