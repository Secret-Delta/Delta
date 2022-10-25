package com.SecretDelta.delta.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class EditTaskActivity extends AppCompatActivity {
    private static final String TAG = "EditTaskActivity";
    private ArrayList<SubTaskModel> taskList = new ArrayList<>();
    private Spinner spinner;
    private ImageButton backBtn, delBtn, calendarBtn, subBtn, btnSave;
    private EditText mTask, mDescription, subtask;
    private Button subTaskButton;
    private TaskModel taskModel;
    private String priority, iTask, iDes, iPriority, iId, iRemindTime, iRemind;
    private int iYear, iMonth, iDay, iHourOfDay, iMinute;

    AddTaskAdapter addTaskAdapter;

    BottomSheetDialog bottomSheetDialog;
    DatabaseReference dbRef;

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

        addTaskAdapter = new AddTaskAdapter(this, taskList);    // create task adapter
        recyclerView.setAdapter(addTaskAdapter);   // set task adapter

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        delBtn = findViewById(R.id.deleteButton);

        calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass values to CalendarPopup activity
                Intent iCalendar = new Intent(EditTaskActivity.this, CalendarPopup.class);
                iCalendar.putExtra("pRemindTime", iRemindTime);
                iCalendar.putExtra("pRemind", iRemind);
                iCalendar.putExtra("pYear", iYear);
                iCalendar.putExtra("pMonth", iMonth);
                iCalendar.putExtra("pDay", iDay);
                iCalendar.putExtra("pHourOfDay", iHourOfDay);
                iCalendar.putExtra("pMinute", iMinute);
                ActivityResultLauncher.launch(iCalendar);
            }
        });


        subBtn = findViewById(R.id.subTaskBtn);

        spinner = findViewById(R.id.prioritySpinner);
        initPrioritySpinner();

        mTask = findViewById(R.id.taskName);
        mDescription = findViewById(R.id.description);

//        priority = spinner.getSelectedItem().toString();

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            iId = intent.getString("mId");
            iTask = intent.getString("mTask");
            iDes = intent.getString("mDes");
            iPriority = intent.getString("mPriority");
            iRemindTime = intent.getString("mRemindTime");
            iRemind = intent.getString("mRemind");
            iYear = intent.getInt("mYear");
            iMonth = intent.getInt("mMonth");
            iDay = intent.getInt("mDay");
            iHourOfDay = intent.getInt("mHourOfDay");
            iMinute = intent.getInt("mMinute");

            mTask.setText(iTask);
            mDescription.setText(iDes);
            priority = iPriority;
        }

        delBtn = findViewById(R.id.deleteButton);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                Query query = dbRef.child("Task").orderByChild("id").equalTo(iId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                            delSnapshot.getRef().removeValue();
                        }

                        Toast.makeText(getApplicationContext(), "Task Deleted Successfully", Toast.LENGTH_SHORT).show();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });

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

                            dataSnap.getRef().child("day").setValue(iDay);
                            dataSnap.getRef().child("month").setValue(iMonth);
                            dataSnap.getRef().child("year").setValue(iYear);
                            dataSnap.getRef().child("hourOfDay").setValue(iHourOfDay);
                            dataSnap.getRef().child("minute").setValue(iMinute);
                            dataSnap.getRef().child("remind").setValue(iRemind);
                            dataSnap.getRef().child("remindTime").setValue(iRemindTime);
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

    // https://stackoverflow.com/a/63654043
    // This method is called when the CalendarPopup activity finishes
    ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        iYear = data.getIntExtra("year", 2022);
                        iMonth = data.getIntExtra("month", 10);
                        iDay = data.getIntExtra("day", 1);

                        iHourOfDay = data.getIntExtra("hour", 1);
                        iMinute = data.getIntExtra("minute", 1);

                        iRemindTime = data.getStringExtra("remindTime");
                        iRemind = data.getStringExtra("remind");
                    }
                }
            });

    @SuppressLint("InflateParams")
    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.sub_task_dialog, null, false);

        subTaskButton = view.findViewById(R.id.subTaskButton);
        subtask = view.findViewById(R.id.subTaskText);

        subTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                String taskID = UUID.randomUUID().toString();

                SubTaskModel subTaskModel = new SubTaskModel();
                subTaskModel.setId(taskID);
                subTaskModel.setTask(subtask.getText().toString().trim());
                subTaskModel.setCheck(0);
                taskList.add(subTaskModel);

                addTaskAdapter.setTasks(taskList);     // set tasks
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