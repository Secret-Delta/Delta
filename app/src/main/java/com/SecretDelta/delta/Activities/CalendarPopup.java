package com.SecretDelta.delta.Activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.SecretDelta.delta.R;

import java.util.Locale;

public class CalendarPopup extends Activity {

    Button setTimeBtn, setRemindBtn;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_calendar);

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setLayout(width, height);

        setTimeBtn = findViewById(R.id.setTimeBtn);

        setRemindBtn = findViewById(R.id.setRemindBtn);
        setRemindBtn.setOnClickListener(v -> startActivity(new Intent(CalendarPopup.this, TaskRemindActivity.class)));

    }

    public void popTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hourOfDay, minutes) -> {
            hour = hourOfDay;
            minute = minutes;
            setTimeBtn.setText(String.format(Locale.getDefault(), "⏰   Set Time                          %02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }
}
