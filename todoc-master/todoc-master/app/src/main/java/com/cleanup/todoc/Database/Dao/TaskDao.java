package com.cleanup.todoc.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     void insert(Task task);

    @Query("DELETE FROM task_table")
    void deletaAll();

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    LiveData<List<Task>> getTask();

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);








}
