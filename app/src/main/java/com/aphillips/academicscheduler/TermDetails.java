package com.aphillips.academicscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.aphillips.academicscheduler.database.AcademicRepository;

public class TermDetails extends AppCompatActivity {

    TextView termName;
    TextView termStart;
    TextView termEnd;
    String sTermName;
    String sTermStart;
    String sTermEnd;
    int termId;
    AcademicRepository academicRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        termName = findViewById(R.id.term_name_detail_textview);
        termStart = findViewById(R.id.term_start_detail_textview);
        termEnd = findViewById(R.id.term_end_detail_textview);
        termId = getIntent().getIntExtra("id", 0);
        sTermName = getIntent().getStringExtra("name");
        sTermStart = getIntent().getStringExtra("start");
        sTermEnd = getIntent().getStringExtra("end");
        termName.setText(sTermName);
        termStart.setText(sTermStart);
        termEnd.setText(sTermEnd);

        academicRepository = new AcademicRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.term_detail_recyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this,
                academicRepository.getTermCourses(termId));
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseAdapter);
    }
}