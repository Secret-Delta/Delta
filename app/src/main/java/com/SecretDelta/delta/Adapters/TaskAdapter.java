package com.SecretDelta.delta.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SecretDelta.delta.Activities.EditTaskActivity;
import com.SecretDelta.delta.Activities.TaskOverviewActivity;
import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskRecyclerViewHolder> {
    private static final String TAG = "TaskAdapter: called";
    private Context context;
    private ArrayList<TaskModel> taskList;

    public TaskAdapter(Context context, ArrayList<TaskModel> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public TaskRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskRecyclerViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        final TaskModel model = taskList.get(position);
        final String task = model.getTask();
        final String priority = model.getPriority();

        ArrayList<SubTaskModel> subTaskList = model.getArrayList();

        holder.taskName.setText(task);
        holder.priority.setText(priority);

        SubTaskAdapter subTaskAdapter = new SubTaskAdapter(context, subTaskList);

        holder.taskRecycler.setHasFixedSize(true);
        holder.taskRecycler.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        holder.taskRecycler.setAdapter(subTaskAdapter);

        holder.taskRecycler.setNestedScrollingEnabled(false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mId = model.getId();
                String mTask = model.getTask();
                String mDes = model.getDescription();
                String mPriority = model.getPriority();
                String mRemindTime = model.getRemindTime();
                String mRemind = model.getRemind();
                int mYear = model.getYear();
                int mMonth = model.getMonth();
                int mDay = model.getDay();
                int mHourOfDay = model.getHourOfDay();
                int mMinute = model.getMinute();

                Context context = v.getContext();
                Intent intent = new Intent(context , EditTaskActivity.class);

                // pass values to EditTaskActivity
                intent.putExtra("mId", mId);
                intent.putExtra("mTask", mTask);
                intent.putExtra("mDes", mDes);
                intent.putExtra("mPriority", mPriority);
                intent.putExtra("mRemindTime", mRemindTime);
                intent.putExtra("mRemind", mRemind);
                intent.putExtra("mYear", mYear);
                intent.putExtra("mMonth", mMonth);
                intent.putExtra("mDay", mDay);
                intent.putExtra("mHourOfDay", mHourOfDay);
                intent.putExtra("mMinute", mMinute);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    @SuppressLint("NonConstantResourceId")
    static class TaskRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.taskName)
        TextView taskName;

        @BindView(R.id.priority)
        TextView priority;

        @BindView(R.id.tasksRecyclerView)
        RecyclerView taskRecycler;

        public TaskRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
