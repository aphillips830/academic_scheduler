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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aphillips.academicscheduler.database.AcademicRepository;
import com.aphillips.academicscheduler.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class TermDetails extends AppCompatActivity {

    TextView termName;
    TextView termStart;
    TextView termEnd;
    String sTermName;
    String sTermStart;
    String sTermEnd;
    int termId;
    AcademicRepository academicRepository;
    CourseAdapter courseAdapter;

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
        courseAdapter = new CourseAdapter(this,
                academicRepository.getTermCourses(termId));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseAdapter);

        // Fab button
        FloatingActionButton addFab = findViewById(R.id.add_course_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });
    }

    private void addCourse() {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_course_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TermDetails.this,
                R.style.MyDialogTheme);
        dialogBuilder.setView(view);

        TextView courseTitle = view.findViewById(R.id.add_course_textview);
        final EditText courseName = view.findViewById(R.id.course_name_edittext);
        final EditText courseStart = view.findViewById(R.id.course_start_edittext);
        final EditText courseEnd = view.findViewById(R.id.course_end_edittext);
        final RadioButton statusInProgress = view.findViewById(R.id.status_inprogress);
        final RadioButton statusCompleted = view.findViewById(R.id.status_completed);
        final RadioButton statusDropped = view.findViewById(R.id.status_dropped);
        final RadioButton statusPlanToTake = view.findViewById(R.id.status_plantotake);
        final EditText instructorName = view.findViewById(R.id.instructor_name_edittext);
        final EditText instructorPhone = view.findViewById(R.id.instructor_phone_edittext);
        final EditText instructorEmail = view.findViewById(R.id.instructor_email_edittext);

        courseTitle.setText(R.string.add_new_course);

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

        // Calendar for course start
        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                int startMonth = calendar.get(Calendar.MONTH);
                int startYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(TermDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                        courseStart.setText(dateString);
                    }
                }, startYear, startMonth, startDay);
                picker.show();
            }
        });

        // Calendar for course start
        courseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int endDay = calendar.get(Calendar.DAY_OF_MONTH);
                int endMonth = calendar.get(Calendar.MONTH);
                int endYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(TermDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                        courseEnd.setText(dateString);
                    }
                }, endYear, endMonth, endDay);
                picker.show();
            }
        });

        // Check for empty fields and add course to database
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(courseName.getText().toString()) ||
                        TextUtils.isEmpty(courseStart.getText().toString()) ||
                        TextUtils.isEmpty(courseEnd.getText().toString())) {
                    Toast.makeText(TermDetails.this,
                                    "Field empty",
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String newCourseName = courseName.getText().toString();
                    String newCourseStart = courseStart.getText().toString();
                    String newCourseEnd = courseEnd.getText().toString();
                    String courseStatus;
                    // get status
                    if (statusInProgress.isChecked()) {
                        courseStatus = "In Progess";
                    } else if (statusCompleted.isChecked()) {
                        courseStatus = "Completed";
                    } else if (statusDropped.isChecked()) {
                        courseStatus = "Dropped";
                    } else if (statusPlanToTake.isChecked()) {
                        courseStatus = "Plan to Take";
                    } else {
                        courseStatus = " ";
                    }
                    String newInstructorName = instructorName.getText().toString();
                    String newInstructorPhone = instructorPhone.getText().toString();
                    String newInstructorEmail = instructorEmail.getText().toString();

                    academicRepository.insert(new Course(0, newCourseName, newCourseStart,
                            newCourseEnd, courseStatus, newInstructorName, newInstructorPhone,
                            newInstructorEmail, " ", termId));

                    Toast.makeText(getApplicationContext(),
                            "Course Added",
                            Toast.LENGTH_SHORT)
                            .show();
                    courseAdapter.setCoursesList(academicRepository.getTermCourses(termId));
                    alertDialog.dismiss();
                }
            }
        });
    }
}