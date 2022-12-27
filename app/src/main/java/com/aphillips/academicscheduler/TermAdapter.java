package com.aphillips.academicscheduler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aphillips.academicscheduler.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private final Context mContext;
    private List<Term> mTermsList;

    public TermAdapter(Context context, List<Term> termList) {
        this.mContext = context;
        this.mTermsList = termList;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item_view, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if (mTermsList != null) {
            Term current = mTermsList.get(position);
            holder.termName.setText(current.getTerm_name());
            holder.termStart.setText(current.getTerm_start_date());
            holder.termEnd.setText(current.getTerm_end_date());
        } else {
            holder.termName.setText(R.string.no_terms);
            holder.termStart.setText(R.string.no_date);
            holder.termEnd.setText(R.string.no_date);
        }
    }

    @Override
    public int getItemCount() {
        return mTermsList.size();
    }

    public void setTermsList(List<Term> terms) {
        mTermsList = terms;
        notifyDataSetChanged();
    }

    static class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termName;
        private final TextView termStart;
        private final TextView termEnd;

        private TermViewHolder(View item_view) {
            super(item_view);
            this.termName = item_view.findViewById(R.id.term_name_textview);
            this.termStart = item_view.findViewById(R.id.term_start_textview);
            this.termEnd = item_view.findViewById(R.id.term_end_textview);
            item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                }
            });
        }
    }
}
