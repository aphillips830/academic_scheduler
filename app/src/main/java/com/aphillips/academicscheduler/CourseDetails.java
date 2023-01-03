package com.aphillips.academicscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aphillips.academicscheduler.database.AcademicRepository;
import com.aphillips.academicscheduler.entities.Assessment;
import com.aphillips.academicscheduler.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    TextView courseName;
    TextView courseStart;
    TextView courseEnd;
    TextView courseStatus;
    TextView instructorName;
    TextView instructorPhone;
    TextView instructorEmail;
    TextView courseNotes;
    int termId;
    int courseId;
    AcademicRepository academicRepository;
    AssessmentAdapter assessmentAdapter;
    Button notesButton;
    Course thisCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseName = findViewById(R.id.course_name_detail_textview);
        courseStart = findViewById(R.id.course_start_detail_textview);
        courseEnd = findViewById(R.id.course_end_detail_textview);
        courseStatus = findViewById(R.id.course_status_detail_textview);
        instructorName = findViewById(R.id.instructor_name_textview);
        instructorPhone = findViewById(R.id.instructor_phone_textview);
        instructorEmail = findViewById(R.id.instructor_email_textview);
        courseNotes = findViewById(R.id.course_notes_textview);

        notesButton = findViewById(R.id.notes_button);

        courseId = getIntent().getIntExtra("id", 0);
        courseName.setText(getIntent().getStringExtra("name"));
        courseStart.setText(getIntent().getStringExtra("start"));
        courseEnd.setText(getIntent().getStringExtra("end"));
        courseStatus.setText(getIntent().getStringExtra("status"));
        instructorName.setText(getIntent().getStringExtra("instructor"));
        instructorPhone.setText(getIntent().getStringExtra("phone"));
        instructorEmail.setText(getIntent().getStringExtra("email"));
        courseNotes.setText(getIntent().getStringExtra("notes"));
        termId = getIntent().getIntExtra("termId", 0);

        thisCourse = new Course(courseId, courseName.getText().toString(),
                courseStart.getText().toString(), courseEnd.getText().toString(),
                courseStatus.getText().toString(), instructorName.getText().toString(),
                instructorPhone.getText().toString(), instructorEmail.getText().toString(),
                courseNotes.getText().toString(), termId);

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

        // Edit notes
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNotes(thisCourse);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        assessmentAdapter.setAssessmentList(academicRepository.getCourseAssessments(courseId));
    }

    // Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_course:
                editCourse();
                return true;

            case R.id.delete_course:
                academicRepository.delete(thisCourse);
                onBackPressed();
                return true;

            case R.id.share_note:
                shareNote();
                return true;

            case R.id.course_start_alert:
                notification(courseStart, courseName.getText().toString() + " begins!");
                return true;

            case R.id.course_end_alert:
                notification(courseEnd, courseName.getText().toString() + " ends");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void notification(TextView time, String notificationText) {
        String courseNotificationTime = time.getText().toString();
        Intent startIntent = new Intent(CourseDetails.this, MyReceiver.class);
        startIntent.putExtra("key", notificationText);
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this,
                ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, parseDate(courseNotificationTime), sender);
        Toast.makeText(CourseDetails.this, "Notification Set",
                Toast.LENGTH_SHORT).show();
    }

    public Long parseDate(String sDate) {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        Date notifyDate = null;
        try {
            notifyDate = sdf.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return notifyDate.getTime();
    }

    public void shareNote() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_TITLE, courseName.getText().toString()
                + " - course notes");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void editCourse() {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_course_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseDetails.this,
                R.style.MyDialogTheme);
        dialogBuilder.setView(view);

        TextView editCourseTitle = view.findViewById(R.id.add_course_textview);
        final EditText editCourseName = view.findViewById(R.id.course_name_edittext);
        final EditText editCourseStart = view.findViewById(R.id.course_start_edittext);
        final EditText editCourseEnd = view.findViewById(R.id.course_end_edittext);
        final RadioButton editStatusInProgress = view.findViewById(R.id.status_inprogress);
        final RadioButton editStatusCompleted = view.findViewById(R.id.status_completed);
        final RadioButton editStatusDropped = view.findViewById(R.id.status_dropped);
        final RadioButton editStatusPlanToTake = view.findViewById(R.id.status_plantotake);
        final EditText editInstructorName = view.findViewById(R.id.instructor_name_edittext);
        final EditText editInstructorPhone = view.findViewById(R.id.instructor_phone_edittext);
        final EditText editInstructorEmail = view.findViewById(R.id.instructor_email_edittext);

        editCourseTitle.setText(R.string.edit_course);
        editCourseName.setText(courseName.getText().toString());
        editCourseStart.setText(courseStart.getText().toString());
        editCourseEnd.setText(courseEnd.getText().toString());
        editInstructorName.setText(instructorName.getText().toString());
        editInstructorPhone.setText(instructorPhone.getText().toString());
        editInstructorEmail.setText(instructorEmail.getText().toString());

        String status = courseStatus.getText().toString();

        switch (status) {
            case "In Progress":
                editStatusInProgress.setChecked(true);
                break;
            case "Completed":
                editStatusCompleted.setChecked(true);
                break;
            case "Dropped":
                editStatusDropped.setChecked(true);
                break;
            case "Plan to Take":
                editStatusPlanToTake.setChecked(true);
                break;
        }

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
        editCourseStart.setOnClickListener(new View.OnClickListener() {
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
                        String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                        editCourseStart.setText(dateString);
                    }
                }, startYear, startMonth, startDay);
                picker.show();
            }
        });

        // Calendar for course end
        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int endDay = calendar.get(Calendar.DAY_OF_MONTH);
                int endMonth = calendar.get(Calendar.MONTH);
                int endYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(CourseDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                        editCourseEnd.setText(dateString);
                    }
                }, endYear, endMonth, endDay);
                picker.show();
            }
        });

        // Check for empty fields and update course in database
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editCourseName.getText().toString()) ||
                        TextUtils.isEmpty(editCourseStart.getText().toString()) ||
                        TextUtils.isEmpty(editCourseEnd.getText().toString())) {
                    Toast.makeText(CourseDetails.this,
                                    "Field empty",
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String updatedCourseName = editCourseName.getText().toString();
                    String updatedCourseStart = editCourseStart.getText().toString();
                    String updatedCourseEnd = editCourseEnd.getText().toString();
                    String updatedCourseStatus;
                    // get status
                    if (editStatusInProgress.isChecked()) {
                        updatedCourseStatus = "In Progress";
                    } else if (editStatusCompleted.isChecked()) {
                        updatedCourseStatus = "Completed";
                    } else if (editStatusDropped.isChecked()) {
                        updatedCourseStatus = "Dropped";
                    } else if (editStatusPlanToTake.isChecked()) {
                        updatedCourseStatus = "Plan to Take";
                    } else {
                        updatedCourseStatus = " ";
                    }
                    String updatedInstructorName = editInstructorName.getText().toString();
                    String updatedInstructorPhone = editInstructorPhone.getText().toString();
                    String updatedInstructorEmail = editInstructorEmail.getText().toString();
                    String updatedCourseNotes = courseNotes.getText().toString();

                    // Update course details
                    courseName.setText(updatedCourseName);
                    courseStart.setText(updatedCourseStart);
                    courseEnd.setText(updatedCourseEnd);
                    courseStatus.setText(updatedCourseStatus);
                    instructorName.setText(updatedInstructorName);
                    instructorPhone.setText(updatedInstructorPhone);
                    instructorEmail.setText(updatedInstructorEmail);
                    courseNotes.setText(updatedCourseNotes);

                    academicRepository.update(new Course(courseId, updatedCourseName,
                            updatedCourseStart, updatedCourseEnd, updatedCourseStatus,
                            updatedInstructorName, updatedInstructorPhone, updatedInstructorEmail,
                            updatedCourseNotes, termId));

                    Toast.makeText(getApplicationContext(),
                                    "Course Updated",
                                    Toast.LENGTH_SHORT)
                            .show();
                    alertDialog.dismiss();
                }
            }
        });
    }

    public void editNotes(Course course) {

        LayoutInflater noteLayoutInflater = LayoutInflater.from(getApplicationContext());
        View noteView = noteLayoutInflater.inflate(R.layout.add_edit_note_layout, null);

        AlertDialog.Builder noteDialogBuilder = new AlertDialog.Builder(CourseDetails.this,
                R.style.MyDialogTheme);
        noteDialogBuilder.setView(noteView);

        final EditText noteEdittext = noteView.findViewById(R.id.edit_notes_edittext);
        noteEdittext.setText(course.getCourse_note());

        noteDialogBuilder.setCancelable(false).setPositiveButton("Save",
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

        final AlertDialog noteAlertDialog = noteDialogBuilder.create();
        noteAlertDialog.show();

        // Save note to database and update note in course details
        noteAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = noteEdittext.getText().toString();
                course.setCourse_note(newNote);
                academicRepository.update(course);
                Toast.makeText(getApplicationContext(), "Notes Updated",
                        Toast.LENGTH_SHORT).show();
                courseNotes.setText(newNote);
                noteAlertDialog.dismiss();
            }
        });
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
                final Calendar calendar = Calendar.getInstance();
                int endDay = calendar.get(Calendar.DAY_OF_MONTH);
                int endMonth = calendar.get(Calendar.MONTH);
                int endYear = calendar.get(Calendar.YEAR);

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