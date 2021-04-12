package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskRepository(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        return new TaskRepository(database.taskDao());
    }

    public static ProjectRepository provideProjectRepository(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        return new ProjectRepository((ProjectDao) database.projectDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskRepository taskRepository = provideTaskRepository(context);
        ProjectRepository projectRepository = provideProjectRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(taskRepository, projectRepository, executor);
    }
}
