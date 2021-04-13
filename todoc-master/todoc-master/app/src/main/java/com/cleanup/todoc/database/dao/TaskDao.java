package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

        @Query("SELECT * FROM Task")
        LiveData<List<Task>> getAllTasks();

        @Query("SELECT * FROM Task WHERE id = :id")
        LiveData<Task> getTask(long id);

        @Insert
        long insertTask(Task task);

        @Update
        int updateTask(Task task);

        @Query("DELETE FROM Task WHERE id = :id")
        int deleteTask(long id);

}
