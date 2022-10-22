package com.SecretDelta.delta.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.SecretDelta.delta.R;
import com.google.firebase.database.FirebaseDatabase;


public class TaskRemindActivity extends Activity {

    private static final String TAG = "TaskRemind";
    private Spinner spinner;
    Button setBtn;
    TextView remindTime;
    String reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_reminder_layout);

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setLayout(width, height);

        spinner = findViewById(R.id.remindSpinner);
        initRemindSpinner();

        Log.d(TAG, String.valueOf(spinner));

        setBtn = findViewById(R.id.setBtn);
        remindTime = findViewById(R.id.remindInput);

    }

    private void initRemindSpinner() {
        Log.d(TAG, "initRemindSpinner: started");
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.remind, R.layout.task_remind_spinner);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);

//        spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                reminder = spinner.getSelectedItem().toString();

//                Toast.makeText(getApplicationContext(), reminder,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

}