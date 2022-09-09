package com.SecretDelta.delta.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {
    private static final String TAG = "SubTaskAdapter: called";
    private Context context;
    private final ArrayList<SubTaskModel> subTaskList;

    public SubTaskAdapter(Context context, ArrayList<SubTaskModel> subTaskList) {
        this.context = context;
        this.subTaskList = subTaskList;
    }

    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new SubTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubTaskViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        final SubTaskModel item = subTaskList.get(position);
        holder.subTask.setText(item.getTask());
        holder.subTask.setChecked(toBoolean(item.getCheck()));
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return subTaskList.size();
    }

    @SuppressLint("NonConstantResourceId")
    static class SubTaskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.taskCheckBox)
        CheckBox subTask;

        public SubTaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}