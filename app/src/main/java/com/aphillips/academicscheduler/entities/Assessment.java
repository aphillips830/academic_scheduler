package com.aphillips.academicscheduler.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;


@Entity(tableName = "assessment_table", foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "course_id", childColumns = "course_id", onDelete = CASCADE))
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int assessment_id;

    @ColumnInfo(name = "assessment_type")
    private String assessment_type;

    @ColumnInfo(name = "assessment_name")
    private String assessment_name;

    @ColumnInfo(name = "assessment_start_date")
    private Date assessment_start_date;

    @ColumnInfo(name = "assessment_end_date")
    private Date assessment_end_date;

    @ColumnInfo(name = "course_id")
    private int course_id;

    @Ignore
    public Assessment() {
    }

    public Assessment(int assessment_id, String assessment_type, String assessment_name,
                      Date assessment_start_date, Date assessment_end_date, int course_id) {
        this.assessment_id = assessment_id;
        this.assessment_type = assessment_type;
        this.assessment_name = assessment_name;
        this.assessment_start_date = assessment_start_date;
        this.assessment_end_date = assessment_end_date;
        this.course_id = course_id;
    }

    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public Date getAssessment_start_date() {
        return assessment_start_date;
    }

    public void setAssessment_start_date(Date assessment_start_date) {
        this.assessment_start_date = assessment_start_date;
    }

    public Date getAssessment_end_date() {
        return assessment_end_date;
    }

    public void setAssessment_end_date(Date assessment_end_date) {
        this.assessment_end_date = assessment_end_date;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
