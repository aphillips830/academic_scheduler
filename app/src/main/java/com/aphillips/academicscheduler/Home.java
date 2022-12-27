package com.aphillips.academicscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button term_button = findViewById(R.id.term_home_button);
        Button course_button = findViewById(R.id.course_home_button);
        Button assessment_button = findViewById(R.id.assessment_home_button);

        term_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, TermList.class);
                startActivity(intent);
            }
        });

        course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, CourseList.class);
                startActivity(intent);
            }
        });

        assessment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AssessmentList.class);
                startActivity(intent);
            }
        });
    }
}