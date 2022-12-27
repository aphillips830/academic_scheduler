package com.aphillips.academicscheduler.database;

import android.app.Application;

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
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Course> mTermCourses;
    private List<Assessment> mCourseAssessments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService executorService =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public AcademicRepository(Application application) {
        AcademicDatabase academicDatabase = AcademicDatabase.getDatabase(application);
        mTermDAO = academicDatabase.termDAO();
        mCourseDAO = academicDatabase.courseDAO();
        mAssessmentDAO = academicDatabase.assessmentDAO();
    }

    public List<Term> getAllTerms() {
        executorService.execute(() -> mAllTerms = mTermDAO.get_all_terms());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Course> getAllCourses() {
        executorService.execute(() -> mAllCourses = mCourseDAO.get_all_courses());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Course> getTermCourses(int termId) {
        executorService.execute(() -> mTermCourses = mCourseDAO.get_term_courses(termId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mTermCourses;
    }

    public List<Assessment> getAssessments() {
        executorService.execute(() -> mAllAssessments = mAssessmentDAO.get_all_assessments());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<Assessment> getCourseAssessments(int courseId) {
        executorService.execute(() -> mCourseAssessments = mAssessmentDAO.get_course_assessments(courseId));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mCourseAssessments;
    }

    public void insert(Term term) {
        executorService.execute(() -> mTermDAO.insert(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        executorService.execute(() -> mTermDAO.update(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        executorService.execute(() -> mTermDAO.delete(term));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        executorService.execute(() -> mCourseDAO.insert(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        executorService.execute(() -> mCourseDAO.update(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        executorService.execute(() -> mCourseDAO.delete(course));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment) {
        executorService.execute(() -> mAssessmentDAO.insert(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        executorService.execute(() -> mAssessmentDAO.update(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        executorService.execute(() -> mAssessmentDAO.delete(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
