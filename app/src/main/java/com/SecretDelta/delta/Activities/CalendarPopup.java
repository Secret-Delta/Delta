package com.SecretDelta.delta.Activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import com.SecretDelta.delta.R;

import java.sql.Time;
import java.util.Locale;

public class CalendarPopup extends Activity {

    Button setTimeBtn;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_calendar);

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setLayout((int) (width), (int) (height));

        setTimeBtn = findViewById(R.id.setTimeBtn);
    }

    public void popTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hourOfDay, minutes) -> {
            hour = hourOfDay;
            minute = minutes;
            setTimeBtn.setText(String.format(Locale.getDefault(), "⏰    Set Time                          %02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }
}
