package com.aphillips.academicscheduler;

import android.content.Context;
import android.content.Intent;
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
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public void setCoursesList(List<Course> courses) {
        mCourseList = courses;
        notifyDataSetChanged();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;

        private CourseViewHolder(View itemView) {
            super(itemView);
            this.courseName = itemView.findViewById(R.id.course_name_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourseList.get(position);
                    Intent intent = new Intent(mContext, CourseDetails.class);
                    intent.putExtra("id", current.getCourse_id());
                    intent.putExtra("name", current.getCourse_name());
                    intent.putExtra("start", current.getCourse_start_date());
                    intent.putExtra("end", current.getCourse_end_date());
                    intent.putExtra("status", current.getCourse_status());
                    intent.putExtra("instructor", current.getInstructor_name());
                    intent.putExtra("phone", current.getInstructor_phone());
                    intent.putExtra("email", current.getInstructor_email());
                    intent.putExtra("notes", current.getCourse_note());
                    intent.putExtra("termId", current.getTerm_id());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}