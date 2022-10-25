package com.SecretDelta.delta.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.R;

import java.util.ArrayList;

public class AddTaskAdapter extends RecyclerView.Adapter<AddTaskAdapter.ViewHolder> {
    private static final String TAG = "AddTaskAdapter: called";
    private Context context;
    private ArrayList<SubTaskModel> taskList;

    public AddTaskAdapter(Context context, ArrayList<SubTaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.main_sub_task_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "AddTaskAdapter onBindViewHolder: called");

        final SubTaskModel item = taskList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getCheck()));
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public int getItemCount() {
        return taskList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(ArrayList<SubTaskModel> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.addTaskCheckBox);
        }
    }
}
