package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    //----- SINGLETON -----//
    private static volatile TaskDatabase INSTANCE;

    //----- DAO -----//
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    //----- INSTANCE -----//
    public static TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "Todocdatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //----- call -----//

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                //----- test task -----//
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("projectId", 1);
                contentValues.put("name", "ceci est un test N");
                contentValues.put("creationTimeStamp", 12);

                db.insert("Task", OnConflictStrategy.IGNORE, contentValues);

                //----- project -----//
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("id", 1L);
                contentValues1.put("name","Projet Tartampion" );
                contentValues1.put("color",0xFFEADAD1);

                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("id", 2L);
                contentValues2.put("name","Projet Lucidia" );
                contentValues2.put("color",0xFFB4CDBA);

                ContentValues contentValues3 = new ContentValues();
                contentValues3.put("id", 3L);
                contentValues3.put("name","Projet Circu" );
                contentValues3.put("color",0xFFA3CED2);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);
            }
        };
    }

}
