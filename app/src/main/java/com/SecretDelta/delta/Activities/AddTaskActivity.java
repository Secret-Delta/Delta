package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddTaskActivity";
    private ArrayList<SubTaskModel> taskList = new ArrayList<>();
    private Spinner spinner;

    ImageButton backBtn, delBtn, calendarBtn, subBtn, btnSave;
    EditText mTask, mDescription;

    DatabaseReference dbRef;
    TaskModel taskModel;

    BottomSheetDialog bottomSheetDialog;
    Button subTaskButton;
    EditText subtask;

    SubTaskModel subTaskModel;

    private void clearControls() {
        mTask.setText("");
        mDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_recycler);

        RecyclerView recyclerView = findViewById(R.id.addTasksRecyclerView);   // get reference to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // set layoutManger

        taskModel = new TaskModel();

//        initTasks();

        AddTaskAdapter taskAdapter = new AddTaskAdapter(taskList);    // create task adapter
        taskAdapter.setTasks(taskList);     // set tasks
        recyclerView.setAdapter(taskAdapter);   // set task adapter

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(v -> startActivity(new Intent(AddTaskActivity.this, CalendarPopup.class)));

        subBtn = findViewById(R.id.subTaskBtn);
//        subBtn.setOnClickListener(v -> startActivity(new Intent(AddTaskActivity.this, AddSubTask.class)));

        spinner = findViewById(R.id.prioritySpinner);
        initPrioritySpinner();

        UUID uuid = UUID.randomUUID();
        long taskID = uuid.getLeastSignificantBits() & Long.MAX_VALUE;
        // long to int

        mTask = findViewById(R.id.taskName);
        mDescription = findViewById(R.id.description);

        String priority = spinner.getSelectedItem().toString();

        delBtn = findViewById(R.id.deleteButton);

        btnSave = findViewById(R.id.saveBtn);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Task");

                try {
                    if(TextUtils.isEmpty(mTask.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a task", Toast.LENGTH_SHORT).show();
                    else {
                        taskModel.setId((int)taskID);
                        taskModel.setTask(mTask.getText().toString().trim());
                        taskModel.setDescription(mDescription.getText().toString().trim());
                        taskModel.setPriority(priority);
                        taskModel.setArrayList(taskList);
                        dbRef.push().setValue(taskModel);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException e) {
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
                UUID uuid = UUID.randomUUID();
                long taskID = uuid.getLeastSignificantBits() & Long.MAX_VALUE;

                subTaskModel.setId((int)taskID);
                subTaskModel.setTask(subtask.getText().toString().trim());
                subTaskModel.setCheck(0);
                taskList.add(subTaskModel);
            }
        });

        bottomSheetDialog.setContentView(view);

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

    private void initPrioritySpinner() {
        Log.d(TAG, "initPrioritySpinner: started");
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