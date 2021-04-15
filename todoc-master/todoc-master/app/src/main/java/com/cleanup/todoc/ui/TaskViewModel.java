package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //----- Repositories -----//
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor executor;

    //----- Data -----//
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    public void init(long id) {
        if (this.currentProject != null) {
            return;
        }
        currentProject = projectRepository.getProject(id);
    }

    //----- For Project -----//
    public LiveData<Project> getProject(long id) {
        return this.currentProject;
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    //----- For Task -----//
    public LiveData<List<Task>> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public LiveData<Task> getTask(long id) {
        return taskRepository.getTask(id);
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskRepository.createTask(task);
        });
    }

    public void deleteTask(long id) {
        executor.execute(() -> {
            taskRepository.deleteTask(id);
        });
    }

    public void updateItem(Task task) {
        executor.execute(() -> {
            taskRepository.updateTask(task);
        });
    }
}
