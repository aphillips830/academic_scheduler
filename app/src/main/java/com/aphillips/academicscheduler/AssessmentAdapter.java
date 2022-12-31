package com.aphillips.academicscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aphillips.academicscheduler.entities.Assessment;
import com.aphillips.academicscheduler.entities.Course;

import java.util.List;

public class AssessmentAdapter  extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private final Context mContext;
    private List<Assessment> mAssessmentList;

    public AssessmentAdapter(Context context, List<Assessment> assessmentList) {
        this.mContext = context;
        this.mAssessmentList = assessmentList;
    }


    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        Assessment current = mAssessmentList.get(position);
        holder.assessmentName.setText(current.getAssessment_name());
        holder.assessmentType.setText(current.getAssessment_type());
    }

    @Override
    public int getItemCount() {
        return mAssessmentList.size();
    }

    public void setAssessmentList(List<Assessment> assessments) {
        mAssessmentList = assessments;
        notifyDataSetChanged();
    }

    static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentType;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            this.assessmentName = itemView.findViewById(R.id.assessment_name_textview);
            this.assessmentType = itemView.findViewById(R.id.assessment_type_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                }
            });
        }
    }
}
