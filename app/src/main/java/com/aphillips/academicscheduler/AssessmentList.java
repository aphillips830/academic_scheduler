package com.aphillips.academicscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aphillips.academicscheduler.database.AcademicRepository;

public class AssessmentList extends AppCompatActivity {

    private AcademicRepository academicRepository;
    AssessmentAdapter assessmentAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        // Display all assessments
        academicRepository = new AcademicRepository(getApplication());
        recyclerView = findViewById(R.id.assessment_recyclerView);
        assessmentAdapter = new AssessmentAdapter(this, academicRepository.getAssessments());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assessmentAdapter);
    }
}