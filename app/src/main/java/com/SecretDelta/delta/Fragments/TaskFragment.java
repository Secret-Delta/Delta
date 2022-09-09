package com.SecretDelta.delta.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SecretDelta.delta.Adapters.TaskAdapter;
import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskFragment extends Fragment {
    private static final String TAG = "TaskActivity";
    private final ArrayList<TaskModel> taskList = new ArrayList<>();
    private Boolean taskLoad = true;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tasksRecyclerView)
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());

        if (taskLoad) {
            initTasks();
            taskLoad = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View view = inflater.inflate(R.layout.recyclerview_layout, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);   // get reference to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  // set layoutManger

        taskAdapter = new TaskAdapter(getActivity(), taskList);    //  create task adapter

        recyclerView.setAdapter(taskAdapter);   // set task adapter

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initTasks() {
        Log.d(TAG, "initTasks: started");
        for (int i = 1; i <= 3; i++) {

            TaskModel taskModel = new TaskModel();

            taskModel.setTask("🎯 Task " + i);

            ArrayList<SubTaskModel> subTask = new ArrayList<>();

            for (int j = 1; j <= 3; j++) {
                SubTaskModel subTaskModel = new SubTaskModel();
                subTaskModel.setTask("Sub Task " + j);
                subTaskModel.setCheck(0);
                subTask.add(subTaskModel);
            }

            taskModel.setArrayList(subTask);

            taskList.add(taskModel);

        }
        // taskAdapter.notifyDataSetChanged();
    }
}