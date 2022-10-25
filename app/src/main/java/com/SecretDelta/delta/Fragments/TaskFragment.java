package com.SecretDelta.delta.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SecretDelta.delta.Activities.AddTaskActivity;
import com.SecretDelta.delta.Activities.TaskOverviewActivity;
import com.SecretDelta.delta.Adapters.TaskAdapter;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskFragment extends Fragment {
    private static final String TAG = "TaskFragment";
    private final ArrayList<TaskModel> taskList = new ArrayList<>();
    private Boolean taskLoad = true;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tasksRecyclerView)
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "TaskFragment onCreate: started");
        super.onCreate(savedInstanceState);
        ButterKnife.bind(requireActivity());

        if (taskLoad) {
            initTasks();
            taskLoad = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View view = inflater.inflate(R.layout.main_recyclerview_layout, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.taskRecyclerView);   // get reference to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  // set layoutManger

        taskAdapter = new TaskAdapter(getActivity(), taskList);    //  create task adapter

        recyclerView.setAdapter(taskAdapter);   // set task adapter

        FloatingActionButton actionButton = view.findViewById(R.id.fab);
        actionButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddTaskActivity.class)));

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initTasks() {
        Log.d(TAG, "initTasks: started");
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Task");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    TaskModel taskModel = dataSnap.getValue(TaskModel.class);
                    taskList.add(taskModel);
                    Collections.reverse(taskList);

                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.taskReport) {
            Intent i = new Intent(getActivity(), TaskOverviewActivity.class);
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}