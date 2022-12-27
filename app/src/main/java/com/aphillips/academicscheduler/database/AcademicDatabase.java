package com.aphillips.academicscheduler.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aphillips.academicscheduler.dao.AssessmentDAO;
import com.aphillips.academicscheduler.dao.CourseDAO;
import com.aphillips.academicscheduler.dao.TermDAO;
import com.aphillips.academicscheduler.entities.Assessment;
import com.aphillips.academicscheduler.entities.Course;
import com.aphillips.academicscheduler.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class},
        version = 1, exportSchema = false)
public abstract class AcademicDatabase extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile AcademicDatabase INSTANCE;

    static AcademicDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AcademicDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AcademicDatabase.class,
                            "academic_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
