package com.aphillips.academicscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aphillips.academicscheduler.entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment_table ORDER BY assessment_id ASC")
    List<Assessment> get_all_assessments();

    @Query("SELECT * FROM assessment_table WHERE " +
            "course_id == :course ORDER BY assessment_id ASC")
    List<Assessment> get_course_assessments(int course);
}
