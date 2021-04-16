package com.cleanup.todoc;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.TaskDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskDaoTest {
    private TaskDatabase database;

    //----- Data for test -----//
    private static final Project Project = new Project(1, "Tartampion", 0xFFEADAD1);
    private static final long ProjectId = Project.getId();
    private static final Task Task1 = new Task(1,ProjectId, "test1", 3);
    private static final Task Task2 = new Task(2,ProjectId, "test2", 2);
    private static final Task Task3 = new Task(3,ProjectId, "test3", 1);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    //----- init hte database -----//
    @Before
    public void initDb() throws Exception
    {
        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TaskDatabase.class)
                .allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception
    {
        this.database.close();
    }
    //----- create 1 project in database and verify if is on database -----//
    @Test
    public void insertProject() throws InterruptedException
    {
        List<Project> projects = TestUtils.getValue(this.database.projectDao().getAllProjects());
        this.database.projectDao().createProject(Project);
        assertEquals(projects.size(), 0);
        projects = TestUtils.getValue(this.database.projectDao().getAllProjects());
        assertEquals(projects.size(), 1);

    }
    //----- verify if database is empty -----//
    @Test
    public void getTasksIsEmpty() throws InterruptedException
    {
        List<Task> tasks = TestUtils.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
    //----- create task and watch if this task is on database -----//
    @Test
    public void insertTask() throws InterruptedException
    {
        List<Task> tasks = TestUtils.getValue(this.database.taskDao().getAllTasks());
        assertEquals(tasks.size(), 0);

        this.database.projectDao().createProject(Project);
        this.database.taskDao().insertTask(Task1);

        tasks = TestUtils.getValue(this.database.taskDao().getAllTasks());
        assertEquals(tasks.size(), 1);
    }

    //----- create 3 task and delete 1  -----//
    @Test
    public void insertAndDeleteTask() throws InterruptedException
    {
        this.database.projectDao().createProject(Project);
        this.database.taskDao().insertTask(Task1);
        this.database.taskDao().insertTask(Task2);
        this.database.taskDao().insertTask(Task3);
        List<Task> tasks = TestUtils.getValue(this.database.taskDao().getAllTasks());
        assertEquals(tasks.size(), 3);
        this.database.taskDao().deleteTask(Task2.id);
        tasks = TestUtils.getValue(this.database.taskDao().getAllTasks());
        assertEquals(tasks.size(), 2);
    }
}
