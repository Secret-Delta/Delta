package com.SecretDelta.delta.Activities;

import androidx.annotation.NonNull;
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
import com.SecretDelta.delta.Fragments.TaskFragment;
import com.SecretDelta.delta.Models.SubTaskModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class EditTaskActivity extends AppCompatActivity {
    private static final String TAG = "AddTaskActivity";
    private ArrayList<SubTaskModel> taskList = new ArrayList<>();
    private Spinner spinner;
    ImageButton backBtn, delBtn, calendarBtn, subBtn, btnSave;
    EditText mTask, mDescription, subtask;
    Button subTaskButton;
    TaskModel taskModel;
    SubTaskModel subTaskModel;
    String priority, iTask, iDes, iPriority, iId;

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
//
//        UUID uuid = UUID.randomUUID();
//        long taskID = uuid.getLeastSignificantBits() & Long.MAX_VALUE;

        // long to int

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
        calendarBtn.setOnClickListener(v -> startActivity(new Intent(EditTaskActivity.this, CalendarPopup.class)));

        subBtn = findViewById(R.id.subTaskBtn);

        spinner = findViewById(R.id.prioritySpinner);
        initPrioritySpinner();

        mTask = findViewById(R.id.taskName);
        mDescription = findViewById(R.id.description);

//        priority = spinner.getSelectedItem().toString();

//        taskModel = getIntent().getParcelableExtra("taskDetails");
//
//        if (taskModel != null) {
//            // on below line we are setting data to our edit text from our modal class.
//            mTask.setText(taskModel.getTask());
//            mDescription.setText(taskModel.getDescription());
////            courseID = courseRVModal.getCourseId();
//        }


        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            iId = intent.getString("mId");
            iTask = intent.getString("mTask");
            iDes = intent.getString("mDes");
            iPriority = intent.getString("mPriority");

            mTask.setText(iTask);
            mDescription.setText(iDes);
            priority = iPriority;
        }

        btnSave = findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Task");

                final String imTask = mTask.getText().toString().trim();
                final String imDes = mDescription.getText().toString().trim();
                final String imPri = priority;

                Query query = dbRef.orderByChild("task").equalTo(iTask);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                            dataSnap.getRef().child("task").setValue(imTask);
                            dataSnap.getRef().child("description").setValue(imDes);
                            dataSnap.getRef().child("priority").setValue(imPri);
                        }

                        Toast.makeText(EditTaskActivity.this, "Task Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTaskActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                // TODO Auto-generated method stub

            }
        });
    }
}