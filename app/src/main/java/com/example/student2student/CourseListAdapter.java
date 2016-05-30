package com.example.student2student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private ArrayList<CourseItem> mDataset;
    private Context ctx;

    public CourseListAdapter(Context context, ArrayList<CourseItem> myDataset) {
        mDataset = myDataset;
        ctx = context;
    }

    public void add(CourseItem item) {
        mDataset.add(item);
    }

    public void add(int position, CourseItem item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(CourseItem item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item_row, parent, false);
        return new ViewHolder(v);
    }

    //setTag is used for getting the right holder while scrolling the list
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.courseName.setTag(position);
        holder.courseName.setText(mDataset.get((int)holder.courseName.getTag()).getCourseName());
        holder.courseName.setChecked(mDataset.get((int)holder.courseName.getTag()).isChecked());
        holder.courseName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDataset.get((int)holder.courseName.getTag()).setIsChecked(isChecked);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox courseName;

        public ViewHolder(final View view) {
            super(view);
            courseName = (CheckBox) view.findViewById(R.id.coursName);
        }
    }

}
