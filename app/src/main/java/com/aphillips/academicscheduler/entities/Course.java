package com.aphillips.academicscheduler.entities;

import static androidx.room.ForeignKey.RESTRICT;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "course_table", foreignKeys = @ForeignKey(entity = Term.class,
        parentColumns = "term_id", childColumns = "term_id", onDelete = RESTRICT))
public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int course_id;

    @ColumnInfo(name = "course_name")
    private String course_name;

    @ColumnInfo(name = "course_start_date")
    private Date course_start_date;

    @ColumnInfo(name = "course_end_date")
    private Date course_end_date;

    @ColumnInfo(name = "course_status")
    private String course_status;

    @ColumnInfo(name = "instructor_name")
    private String instructor_name;

    @ColumnInfo(name = "instructor_phone")
    private String instructor_phone;

    @ColumnInfo(name = "instructor_email")
    private String instructor_email;

    @ColumnInfo(name = "course_note")
    private String course_note;

    @ColumnInfo(name = "term_id")
    private int term_id;

    @Ignore
    public Course () {
    }

    public Course(int course_id, String course_name, Date course_start_date,
                  Date course_end_date, String course_status, String instructor_name,
                  String instructor_phone, String instructor_email, String course_note,
                  int term_id) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_start_date = course_start_date;
        this.course_end_date = course_end_date;
        this.course_status = course_status;
        this.instructor_name = instructor_name;
        this.instructor_phone = instructor_phone;
        this.instructor_email = instructor_email;
        this.course_note = course_note;
        this.term_id = term_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Date getCourse_start_date() {
        return course_start_date;
    }

    public void setCourse_start_date(Date course_start_date) {
        this.course_start_date = course_start_date;
    }

    public Date getCourse_end_date() {
        return course_end_date;
    }

    public void setCourse_end_date(Date course_end_date) {
        this.course_end_date = course_end_date;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getInstructor_phone() {
        return instructor_phone;
    }

    public void setInstructor_phone(String instructor_phone) {
        this.instructor_phone = instructor_phone;
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
    }

    public String getCourse_note() {
        return course_note;
    }

    public void setCourse_note(String course_note) {
        this.course_note = course_note;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }
}
