package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    //----- data -----//
    private final ProjectDao projectDao;


    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //----- Get project -----//
    public LiveData<Project> getProject(long id) {
        return this.projectDao.getProject(id);
    }

    //-----Get all projects  -----//
    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }

}
