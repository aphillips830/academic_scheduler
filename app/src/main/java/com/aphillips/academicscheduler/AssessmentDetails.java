package com.aphillips.academicscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aphillips.academicscheduler.database.AcademicRepository;
import com.aphillips.academicscheduler.entities.Assessment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    TextView assessName;
    TextView assessType;
    TextView assessStart;
    TextView assessEnd;
    int assessId;
    int courseId;
    AcademicRepository academicRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assessName = findViewById(R.id.assess_name_detail_textview);
        assessType = findViewById(R.id.assess_type_detail_textview);
        assessStart = findViewById(R.id.assess_start_detail_textview);
        assessEnd = findViewById(R.id.assess_end_detail_textview);

        academicRepository = new AcademicRepository(getApplication());

        assessName.setText(getIntent().getStringExtra("name"));
        assessType.setText(getIntent().getStringExtra("type"));
        assessStart.setText(getIntent().getStringExtra("start"));
        assessEnd.setText(getIntent().getStringExtra("end"));
        assessId = getIntent().getIntExtra("id", 0);
        courseId = getIntent().getIntExtra("courseId", 0);
    }

    // Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_assessment:
                editTerm();
                return true;

            case R.id.delete_assessment:
                academicRepository.delete(new Assessment(assessId, assessType.getText().toString(),
                        assessName.getText().toString(), assessStart.getText().toString(),
                        assessEnd.getText().toString(), courseId));
                onBackPressed();
                return true;

            case R.id.assessment_start_alert:
                notification(assessStart, "Test time : "
                        + assessName.getText().toString());
                return true;

            case R.id.assessment_end_alert:
                notification(assessEnd, "Ending soon : "
                        + assessName.getText().toString());
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
        Intent startIntent = new Intent(AssessmentDetails.this, MyReceiver.class);
        startIntent.putExtra("key", notificationText);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,
                ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, parseDate(courseNotificationTime), sender);
        Toast.makeText(AssessmentDetails.this, "Notification Set",
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

    public void editTerm() {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.add_assessment_layout, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssessmentDetails.this,
                R.style.MyDialogTheme);
        dialogBuilder.setView(view);

        TextView assessTitle = view.findViewById(R.id.add_assessment_textview);
        final EditText editAssessName = view.findViewById(R.id.assessment_name_edittext);
        final EditText editAssessStart = view.findViewById(R.id.assessment_start_edittext);
        final EditText editAssessEnd = view.findViewById(R.id.assessment_end_edittext);
        final RadioButton performance = view.findViewById(R.id.performance_type);
        final RadioButton objective = view.findViewById(R.id.objective_type);

        assessTitle.setText(R.string.edit_assessment);
        editAssessName.setText(assessName.getText().toString());
        editAssessStart.setText(assessStart.getText().toString());
        editAssessEnd.setText(assessEnd.getText().toString());

        if (assessType.getText().toString().equals("Objective")) {
            objective.setChecked(true);
        } else {
            performance.setChecked(true);
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

        // Calendar for assessment start
        editAssessStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                int startMonth = calendar.get(Calendar.MONTH);
                int startYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(AssessmentDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month +1) + "/" + dayOfMonth + "/" + year;
                        editAssessStart.setText(dateString);
                    }
                }, startYear, startMonth, startDay);
                picker.show();
            }
        });

        // Calendar for assessment end
        editAssessEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int endDay = calendar.get(Calendar.DAY_OF_MONTH);
                int endMonth = calendar.get(Calendar.MONTH);
                int endYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(AssessmentDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateString = (month +1) + "/" + dayOfMonth + "/" + year;
                        editAssessEnd.setText(dateString);
                    }
                }, endYear, endMonth, endDay);
                picker.show();
            }
        });

        // Check for empty fields and update assessment in database
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editAssessName.getText().toString()) ||
                        TextUtils.isEmpty(editAssessStart.getText().toString()) ||
                        TextUtils.isEmpty(editAssessEnd.getText().toString())) {
                    Toast.makeText(AssessmentDetails.this,
                                    "Field empty",
                                    Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String updatedAssessName = editAssessName.getText().toString();
                    String updatedAssessStart = editAssessStart.getText().toString();
                    String updatedAssessEnd = editAssessEnd.getText().toString();
                    String updatedAssessType;
                    // Get assessment type
                    if (performance.isChecked()) {
                        updatedAssessType = "Performance";
                    } else if (objective.isChecked()) {
                        updatedAssessType = "Objective";
                    } else {
                        updatedAssessType = " ";
                    }

                    assessName.setText(updatedAssessName);
                    assessType.setText(updatedAssessType);
                    assessStart.setText(updatedAssessStart);
                    assessEnd.setText(updatedAssessEnd);

                    academicRepository.update(new Assessment(assessId, updatedAssessType,
                            updatedAssessName, updatedAssessStart, updatedAssessEnd, courseId));
                    Toast.makeText(getApplicationContext(),
                                    "Assessment Updated",
                                    Toast.LENGTH_SHORT)
                            .show();
                    alertDialog.dismiss();
                }
            }
        });
    }
}