package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.SecretDelta.delta.Adapters.AddTaskAdapter;
import com.SecretDelta.delta.Fragments.TaskFragment;
import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddTaskActivity";
    private ArrayList<SubTaskModel> taskList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_recycler);

        RecyclerView recyclerView = findViewById(R.id.addTasksRecyclerView);   // get reference to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // set layoutManger

        initTasks();

        AddTaskAdapter taskAdapter = new AddTaskAdapter(taskList);    // create task adapter
        taskAdapter.setTasks(taskList);     // set tasks
        recyclerView.setAdapter(taskAdapter);   // set task adapter

        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        ImageButton calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(v -> startActivity(new Intent(AddTaskActivity.this, CalendarPopup.class)));

        spinner = findViewById(R.id.prioritySpinner);
        initSpinner();

    }

    private void initTasks() {
        Log.d(TAG, "initTasks: started");
        SubTaskModel task = new SubTaskModel();
        task.setTask("Task 1");
        task.setCheck(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

    }

    private void initSpinner() {
        Log.d(TAG, "initSpinner: started");
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.priority, android.R.layout.simple_spinner_dropdown_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
//        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}