package com.aphillips.academicscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aphillips.academicscheduler.database.AcademicRepository;

public class CourseList extends AppCompatActivity {

    CourseAdapter courseAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // Display all courses
        AcademicRepository academicRepository = new AcademicRepository(getApplication());
        recyclerView = findViewById(R.id.course_recyclerView);
        courseAdapter = new CourseAdapter(this, academicRepository.getAllCourses());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseAdapter);
    }
}