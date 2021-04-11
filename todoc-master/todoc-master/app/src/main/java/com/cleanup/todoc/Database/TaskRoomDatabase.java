package com.cleanup.todoc.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.cleanup.todoc.Database.Dao.TaskDao;
import com.cleanup.todoc.model.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class TaskRoomDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    private static volatile TaskRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "task_database")
                            .addCallback(mTaskRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static TaskRoomDatabase.Callback mTaskRoomDatabaseCallback = new TaskRoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            databaseWriteExecutor.execute(() -> {
                TaskDao dao = INSTANCE.taskDao();
                dao.deletaAll();

                Task task = new Task(1, 1, "test n1" , 1);
                dao.insert(task);
                task = new Task(2, 3, "test n2" , 2);
                dao.insert(task);
            });
        }
    };

}
