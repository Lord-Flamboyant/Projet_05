package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    //----- GetAll  -----//
    public LiveData<List<Task>> getAllTasks() {
        return this.taskDao.getAllTasks();
    }

    //----- GET task -----//
    public LiveData<Task> getTask(long id) {
        return taskDao.getTask(id);
    }

    //----- Create -----//
    public void createTask(Task task) {
        taskDao.insertTask(task);
    }

    //----- Delete -----//
    public void deleteTask(long id) {
        taskDao.deleteTask(id);
    }

    //----- updateTask -----//
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }
}
