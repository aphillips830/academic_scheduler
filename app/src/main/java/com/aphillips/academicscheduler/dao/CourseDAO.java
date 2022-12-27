package com.aphillips.academicscheduler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aphillips.academicscheduler.entities.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course_table ORDER BY course_start_date ASC")
    List<Course> get_all_courses();

    @Query("SELECT * FROM course_table WHERE term_id == :term ORDER BY course_start_date ASC")
    List<Course> get_term_courses(int term);
}
