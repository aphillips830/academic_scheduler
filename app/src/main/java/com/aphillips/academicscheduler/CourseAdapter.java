package com.aphillips.academicscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aphillips.academicscheduler.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final Context mContext;
    private List<Course> mCourseList;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.mContext = context;
        this.mCourseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item_view, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course current = mCourseList.get(position);
        holder.courseName.setText(current.getCourse_name());
        holder.courseTerm.setText(current.getTerm_id());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public void setCoursesList(List<Course> courses) {
        mCourseList = courses;
        notifyDataSetChanged();
    }


    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;
        private final TextView courseTerm;

        private CourseViewHolder(View itemView) {
            super(itemView);
            this.courseName = itemView.findViewById(R.id.course_name_textview);
            this.courseTerm = itemView.findViewById(R.id.course_term_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                }
            });

        }
    }
}