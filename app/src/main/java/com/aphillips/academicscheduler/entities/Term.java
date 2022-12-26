package com.aphillips.academicscheduler.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int term_id;

    @ColumnInfo(name = "term_name")
    private String term_name;

    @ColumnInfo(name = "term_start_date")
    private Date term_start_date;

    @ColumnInfo(name = "term_end_date")
    private Date term_end_date;

    @Ignore
    public Term() {
    }

    public Term(int term_id, String term_name, Date term_start_date, Date term_end_date) {
        this.term_id = term_id;
        this.term_name = term_name;
        this.term_start_date = term_start_date;
        this.term_end_date = term_end_date;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    public Date getTerm_start_date() {
        return term_start_date;
    }

    public void setTerm_start_date(Date term_start_date) {
        this.term_start_date = term_start_date;
    }

    public Date getTerm_end_date() {
        return term_end_date;
    }

    public void setTerm_end_date(Date term_end_date) {
        this.term_end_date = term_end_date;
    }
}
