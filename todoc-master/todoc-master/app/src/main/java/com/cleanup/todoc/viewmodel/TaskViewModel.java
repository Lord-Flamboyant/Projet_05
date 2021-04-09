package com.cleanup.todoc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;
    private final LiveData<List<Task>> mAllTask;

    public TaskViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTask = mRepository.getAllTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    public void insert(Task task) {
        mRepository.insert(task);
    }

    public void delete(Task task) {
        mRepository.delete(task);
    }

    public void update(Task task) {
        mRepository.update(task);
    }

}
