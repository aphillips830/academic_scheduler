package com.aphillips.academicscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aphillips.academicscheduler.database.AcademicRepository;

public class TermList extends AppCompatActivity {

    private AcademicRepository academicRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        academicRepository = new AcademicRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.term_recyclerview);
        final TermAdapter termAdapter = new TermAdapter(this, academicRepository.getAllTerms());
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}