package com.aphillips.academicscheduler.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.aphillips.academicscheduler.dao.AssessmentDAO;
import com.aphillips.academicscheduler.dao.CourseDAO;
import com.aphillips.academicscheduler.dao.TermDAO;
import com.aphillips.academicscheduler.entities.Assessment;
import com.aphillips.academicscheduler.entities.Course;
import com.aphillips.academicscheduler.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcademicRepository {

    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private LiveData<List<Term>> mAllTerms;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Assessment>> mAllAssessments;

    public AcademicRepository(Application application) {
        AcademicDatabase academicDatabase = AcademicDatabase.getDatabase(application);
        mTermDAO = academicDatabase.termDAO();
        mCourseDAO = academicDatabase.courseDAO();
        mAssessmentDAO = academicDatabase.assessmentDAO();
    }

    public LiveData<List<Term>> getTerms() {
        return mTermDAO.get_all_terms();
    }

    public LiveData<List<Course>> getAllCourses() {
        return mCourseDAO.get_all_courses();
    }

    public LiveData<List<Course>> getTermCourses(int termId) {
        return mCourseDAO.get_term_courses(termId);
    }

    public LiveData<List<Assessment>> getAssessments() {
        return mAssessmentDAO.get_all_assessments();
    }

    public LiveData<List<Assessment>> getCourseAssessments(int courseId) {
        return mAssessmentDAO.get_course_assessments(courseId);
    }

    public void insert(Term term) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mTermDAO.insert(term);
            }
        });
    }

    public void update(Term term) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mTermDAO.update(term);
            }
        });
    }

    public void delete(Term term) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mTermDAO.delete(term);
            }
        });
    }

    public void insert(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCourseDAO.insert(course);
            }
        });
    }

    public void update(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCourseDAO.update(course);
            }
        });
    }

    public void delete(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCourseDAO.delete(course);
            }
        });
    }

    public void insert(Assessment assessment) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mAssessmentDAO.insert(assessment);
            }
        });
    }

    public void update(Assessment assessment) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mAssessmentDAO.update(assessment);
            }
        });
    }

    public void delete(Assessment assessment) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mAssessmentDAO.delete(assessment);
            }
        });
    }
}
