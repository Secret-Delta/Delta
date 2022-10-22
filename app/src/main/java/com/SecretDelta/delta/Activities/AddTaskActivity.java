package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.SecretDelta.delta.Adapters.AddTaskAdapter;
import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {
    private static final String TAG = "AddTaskActivity";
    private ArrayList<SubTaskModel> taskList = new ArrayList<>();
    private Spinner spinner;
    ImageButton backBtn, delBtn, calendarBtn, subBtn, btnSave;
    EditText mTask, mDescription, subtask;
    Button subTaskButton;
    TaskModel taskModel;
    SubTaskModel subTaskModel;
    String priority;

    DatabaseReference dbRef;
    BottomSheetDialog bottomSheetDialog;

    private void clearControls() {
        mTask.setText("");
        mDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_recycler);

        String taskID = UUID.randomUUID().toString();

        RecyclerView recyclerView = findViewById(R.id.addTasksRecyclerView);   // get reference to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // set layoutManger

        taskModel = new TaskModel();

//        initTasks();

        AddTaskAdapter taskAdapter = new AddTaskAdapter(taskList);    // create task adapter
        taskAdapter.setTasks(taskList);     // set tasks
        recyclerView.setAdapter(taskAdapter);   // set task adapter

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        delBtn = findViewById(R.id.deleteButton);

        calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(v -> startActivity(new Intent(AddTaskActivity.this, CalendarPopup.class)));

        subBtn = findViewById(R.id.subTaskBtn);

        spinner = findViewById(R.id.prioritySpinner);
        initPrioritySpinner();

        mTask = findViewById(R.id.taskName);
        mDescription = findViewById(R.id.description);

//        priority = spinner.getSelectedItem().toString();

        btnSave = findViewById(R.id.saveBtn);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Task");

                try {
                    if (TextUtils.isEmpty(mTask.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a task", Toast.LENGTH_SHORT).show();
                    else {
                        taskModel.setId(taskID);
                        taskModel.setTask(mTask.getText().toString().trim());
                        taskModel.setDescription(mDescription.getText().toString().trim());
                        taskModel.setPriority(priority);
                        taskModel.setArrayList(taskList);
                        dbRef.push().setValue(taskModel);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Number", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bottomSheetDialog = new BottomSheetDialog(this);
        createDialog();

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        bottomSheetDialog.getWindow().setLayout(width, height);
    }

    private void initTasks() {
        Log.d(TAG, "initTasks: started");
//        SubTaskModel task = new SubTaskModel();
//        task.setTask("Task 1");
//        task.setCheck(0);
//        task.setId(1);
//
//        taskList.add(task);

        for (int i = 1; i <= 7; i++) {
            SubTaskModel subTaskModel = new SubTaskModel();
            subTaskModel.setTask("Sub Task " + i);
            subTaskModel.setCheck(0);
            taskList.add(subTaskModel);
        }
    }

    @SuppressLint("InflateParams")
    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.sub_task_dialog, null, false);

        subTaskButton = view.findViewById(R.id.subTaskButton);
        subtask = view.findViewById(R.id.subTaskText);

        subTaskModel = new SubTaskModel();
        subTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                String taskID = UUID.randomUUID().toString();

                subTaskModel.setId(taskID);
                subTaskModel.setTask(subtask.getText().toString().trim());
                subTaskModel.setCheck(0);
                taskList.add(subTaskModel);
            }
        });

        bottomSheetDialog.setContentView(view);

    }

    private void initPrioritySpinner() {
        Log.d(TAG, "initPrioritySpinner: started");
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.priority, android.R.layout.simple_spinner_dropdown_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);

//        spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                priority = spinner.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(), priority,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

}