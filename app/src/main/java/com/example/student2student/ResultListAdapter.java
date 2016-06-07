package com.example.student2student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {
    private ArrayList<ItemResult> mDataset;
    private Context ctx;

    public ResultListAdapter(Context context, ArrayList<ItemResult> myDataset) {
        mDataset = myDataset;
        ctx = context;
    }

    public void add(ItemResult item) {
        mDataset.add(item);
    }

    public void add(int position, ItemResult item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(ItemResult item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultitems, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.course.setText(mDataset.get(position).getCourse());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, course;

        public ViewHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            course = (TextView) view.findViewById(R.id.course);
        }
    }
}
