package com.aphillips.academicscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aphillips.academicscheduler.database.AcademicRepository;
import com.aphillips.academicscheduler.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class TermList extends AppCompatActivity {

    private AcademicRepository academicRepository;
    TermAdapter termAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // Display List of Terms
        academicRepository = new AcademicRepository(getApplication());
        recyclerView = findViewById(R.id.term_recyclerview);
        termAdapter = new TermAdapter(this, academicRepository.getAllTerms());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(termAdapter);

        // Fab button
        FloatingActionButton addFab = findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTerm();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        termAdapter.setTermsList(academicRepository.getAllTerms());
    }

    // Add new term via an alertdialog
    public void addTerm() {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_term_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TermList.this,
                R.style.MyDialogTheme);
        dialogBuilder.setView(view);

        TextView termTitle = view.findViewById(R.id.add_edit_textview);
        final EditText termName = view.findViewById(R.id.term_name_edittext);
        final EditText termStart = view.findViewById(R.id.term_start_edittext);
        final EditText termEnd = view.findViewById(R.id.term_end_edittext);

        termTitle.setText(R.string.add_new_term);

        dialogBuilder.setCancelable(false).setPositiveButton
                ("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        // Display a calendar to get term start date
        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                int startMonth = calendar.get(Calendar.MONTH);
                int startYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(TermList.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                        termStart.setText(dateString);
                    }
                }, startYear, startMonth, startDay);
                picker.show();
            }
        });

        // Display a calendar to get term end date
        termEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int endDay = calendar.get(Calendar.DAY_OF_MONTH);
                int endMonth = calendar.get(Calendar.MONTH);
                int endYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(TermList.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                        termEnd.setText(dateString);
                    }
                }, endYear, endMonth, endDay);
                picker.show();
            }
        });

        // Check for empty fields and add new term to database
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(termName.getText().toString()) ||
                        TextUtils.isEmpty(termStart.getText().toString()) ||
                        TextUtils.isEmpty(termEnd.getText().toString())) {
                    Toast.makeText(TermList.this,
                                    "Field empty",
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String newTermName = termName.getText().toString();
                    String newTermStart = termStart.getText().toString();
                    String newTermEnd = termEnd.getText().toString();
                    academicRepository.insert(
                            new Term(0, newTermName, newTermStart, newTermEnd));
                    Toast.makeText(getApplicationContext(),
                                    "Term Added",
                                    Toast.LENGTH_SHORT)
                            .show();
                    termAdapter.setTermsList(academicRepository.getAllTerms());
                    alertDialog.dismiss();
                }
            }
        });
    }
}