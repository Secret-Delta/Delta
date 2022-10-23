package com.SecretDelta.delta.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.R;


public class TaskRemindActivity extends Activity {

    private static final String TAG = "TaskRemind";
    private Spinner spinner;
    private Button setBtn, cancelBtn;
    private TextView remindTime;
    private String reminder;

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


        remindTime = findViewById(R.id.remindInput);
        setBtn = findViewById(R.id.setBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passTRemind = remindTime.getText().toString();
                String passTSpin = reminder;

                Intent intent = new Intent();
                intent.putExtra("remindTime", passTRemind);
                intent.putExtra("remind", passTSpin);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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