package com.aphillips.academicscheduler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aphillips.academicscheduler.entities.Assessment;

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
    }

    @Override
    public int getItemCount() {
        return mAssessmentList.size();
    }

    public void setAssessmentList(List<Assessment> assessments) {
        mAssessmentList = assessments;
        notifyDataSetChanged();
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            this.assessmentName = itemView.findViewById(R.id.assessment_name_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessmentList.get(position);
                    Intent intent = new Intent(mContext, AssessmentDetails.class);
                    intent.putExtra("id", current.getAssessment_id());
                    intent.putExtra("name", current.getAssessment_name());
                    intent.putExtra("type", current.getAssessment_type());
                    intent.putExtra("start", current.getAssessment_start_date());
                    intent.putExtra("end", current.getAssessment_end_date());
                    intent.putExtra("courseId", current.getCourse_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
