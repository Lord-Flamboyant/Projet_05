package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.Database.Dao.TaskDao;
import com.cleanup.todoc.Database.TaskRoomDatabase;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTask;

    public TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTask = mTaskDao.getTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    public void insert(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    public void delete(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.delete(task);
        });
    }

    public void update(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.update(task);
        });
    }

}
