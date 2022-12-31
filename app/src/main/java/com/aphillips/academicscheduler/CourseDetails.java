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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aphillips.academicscheduler.database.AcademicRepository;
import com.aphillips.academicscheduler.entities.Assessment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Objects;

public class CourseDetails extends AppCompatActivity {

    TextView courseName;
    TextView courseStart;
    TextView courseEnd;
    TextView courseStatus;
    TextView instructorName;
    TextView instructorPhone;
    TextView instructorEmail;
    TextView courseNotes;
    int courseId;
    AcademicRepository academicRepository;
    AssessmentAdapter assessmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Back arrow
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        courseName = findViewById(R.id.course_name_detail_textview);
        courseStart = findViewById(R.id.course_start_detail_textview);
        courseEnd = findViewById(R.id.course_end_detail_textview);
        courseStatus = findViewById(R.id.course_status_detail_textview);
        instructorName = findViewById(R.id.instructor_name_textview);
        instructorPhone = findViewById(R.id.instructor_phone_textview);
        instructorEmail = findViewById(R.id.instructor_email_textview);
        courseNotes = findViewById(R.id.course_notes_textview);

        courseId = getIntent().getIntExtra("id", 0);
        courseName.setText(getIntent().getStringExtra("name"));
        courseStart.setText(getIntent().getStringExtra("start"));
        courseEnd.setText(getIntent().getStringExtra("end"));
        courseStatus.setText(getIntent().getStringExtra("status"));
        instructorName.setText(getIntent().getStringExtra("instructor"));
        instructorPhone.setText(getIntent().getStringExtra("phone"));
        instructorEmail.setText(getIntent().getStringExtra("email"));
        courseNotes.setText(getIntent().getStringExtra("notes"));

        academicRepository = new AcademicRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.course_detail_recyclerview);
        assessmentAdapter = new AssessmentAdapter(this,
                academicRepository.getCourseAssessments(courseId));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assessmentAdapter);

        // Fab button
        FloatingActionButton addFab = findViewById(R.id.add_assessment_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssessment();
            }
        });

    }

    // Back arrow
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();;
        return true;
    }

    public void addAssessment() {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_assessment_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseDetails.this,
                R.style.MyDialogTheme);
        dialogBuilder.setView(view);

        TextView assessTitle = view.findViewById(R.id.add_assessment_textview);
        final EditText assessName = view.findViewById(R.id.assessment_name_edittext);
        final EditText assessStart = view.findViewById(R.id.assessment_start_edittext);
        final EditText assessEnd = view.findViewById(R.id.assessment_end_edittext);
        final RadioButton performance = view.findViewById(R.id.performance_type);
        final RadioButton objective = view.findViewById(R.id.objective_type);

        assessTitle.setText(R.string.add_new_assessment);

        dialogBuilder.setCancelable(false).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
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

        // Calendar for assessment start
        assessStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                int startMonth = calendar.get(Calendar.MONTH);
                int startYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(CourseDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month +1) + "/" + dayOfMonth + "/" + year;
                        assessStart.setText(dateString);
                    }
                }, startYear, startMonth, startDay);
                picker.show();
            }
        });

        // Calendar for assessment end
        assessEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calandar = Calendar.getInstance();
                int endDay = calandar.get(Calendar.DAY_OF_MONTH);
                int endMonth = calandar.get(Calendar.MONTH);
                int endYear = calandar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(CourseDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month +1) + "/" + dayOfMonth + "/" + year;
                        assessEnd.setText(dateString);
                    }
                }, endYear, endMonth, endDay);
                picker.show();
            }
        });

        // Add assessment to database
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(assessName.getText().toString()) ||
                        TextUtils.isEmpty(assessStart.getText().toString()) ||
                        TextUtils.isEmpty(assessEnd.getText().toString())) {
                    Toast.makeText(CourseDetails.this,
                            "Field empty",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String newAssessName = assessName.getText().toString();
                    String newAssessStart = assessStart.getText().toString();
                    String newAssessEnd = assessEnd.getText().toString();
                    String courseType;
                    // Get assessment type
                    if (performance.isChecked()) {
                        courseType = "Performance";
                    } else if (objective.isChecked()) {
                        courseType = "Objective";
                    } else {
                        courseType = " ";
                    }

                    academicRepository.insert(new Assessment(0, courseType,
                            newAssessName, newAssessStart, newAssessEnd, courseId));
                    Toast.makeText(getApplicationContext(),
                            "Assessment Added",
                            Toast.LENGTH_SHORT)
                            .show();
                    assessmentAdapter.setAssessmentList(academicRepository
                            .getCourseAssessments(courseId));
                    alertDialog.dismiss();
                }
            }
        });
    }
}