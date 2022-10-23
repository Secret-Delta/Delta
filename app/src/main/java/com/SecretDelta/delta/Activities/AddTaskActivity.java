package com.SecretDelta.delta.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
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
    private ImageButton backBtn, delBtn, calendarBtn, subBtn, btnSave;
    private EditText mTask, mDescription, subtask;
    private Button subTaskButton;
    private TaskModel taskModel;
    private SubTaskModel subTaskModel;
    private String priority, remindTime, remind;
    private int year, month, day, hourOfDay, minute;
    String staskID;
    BottomSheetDialog bottomSheetDialog;
    DatabaseReference dbRef;
    AddTaskAdapter taskAdapter;

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

        taskAdapter = new AddTaskAdapter(this, taskList);    // create task adapter

        recyclerView.setAdapter(taskAdapter);   // set task adapter

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        delBtn = findViewById(R.id.deleteButton);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this, CalendarPopup.class);
                ActivityResultLauncher.launch(intent);
            }
        });

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
                        taskModel.setYear(year);
                        taskModel.setMonth(month);
                        taskModel.setDay(day);
                        taskModel.setHourOfDay(hourOfDay);
                        taskModel.setMinute(minute);
                        taskModel.setRemindTime(remindTime);
                        taskModel.setRemind(remind);
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
                staskID = UUID.randomUUID().toString();

                bottomSheetDialog.show();
            }
        });

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        bottomSheetDialog.getWindow().setLayout(width, height);
    }

    // https://stackoverflow.com/a/63654043
    // This method is called when the CalendarPopup activity finishes
    ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        year = data.getIntExtra("year", 2022);
                        month = data.getIntExtra("month", 10);
                        day = data.getIntExtra("day", 1);

                        hourOfDay = data.getIntExtra("hour", 1);
                        minute = data.getIntExtra("minute", 1);

                        remindTime = data.getStringExtra("remindTime");
                        remind = data.getStringExtra("remind");
                    }
                }
            });

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

                subTaskModel.setId(staskID);
                Log.e("di", staskID);
                subTaskModel.setTask(subtask.getText().toString().trim());
                subTaskModel.setCheck(0);
                taskList.add(subTaskModel);

                taskAdapter.setTasks(taskList);     // set tasks

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