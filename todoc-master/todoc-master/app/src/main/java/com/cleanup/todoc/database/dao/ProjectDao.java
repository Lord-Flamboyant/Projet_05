package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    //----- create project -----//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    //----- get project by id -----//
    @Query("SELECT * FROM Project WHERE id = :id")
    LiveData<Project> getProject(long id);

    //----- get all project -----//
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();
}
