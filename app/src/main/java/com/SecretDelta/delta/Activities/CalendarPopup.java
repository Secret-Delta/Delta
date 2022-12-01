package com.SecretDelta.delta.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.R;
import com.SecretDelta.delta.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Locale;

public class CalendarPopup extends Activity {

    private CalendarView calendarView;
    private Button setTimeBtn, setRemindBtn, cancelBtn, doneBtn;
    private TextView timeText, remindText;
    private int hour, minute, Day, Year, Month;
    private String currentDate, receiveRemindTime, receiveRemind, reminder, aTask;
    private ActivityMainBinding binding;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_calendar);


        setTimeBtn = findViewById(R.id.setTimeBtn);
        timeText = findViewById(R.id.timeText);

        // get values from EditTaskActivity
        Intent intent = getIntent();
        hour = intent.getIntExtra("pHourOfDay", 10);
        minute = intent.getIntExtra("pMinute", 00);
        timeText.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

        Year = intent.getIntExtra("pYear", 2022);
        Month = intent.getIntExtra("pMonth", 1);
        Day = intent.getIntExtra("pDay", 1);

        aTask = intent.getStringExtra("aTask");

        // Calendar view
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setLayout(width, height);

        calendarView = findViewById(R.id.taskCalendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Day = dayOfMonth;
                Year = year;
                Month = month + 1;

                currentDate = Year + "/" + Month + "/" + Day;
            }
        });

        remindText = findViewById(R.id.remindText);

        receiveRemindTime = intent.getStringExtra("pRemindTime");
        receiveRemind = intent.getStringExtra("pRemind");

        if(receiveRemindTime == null){
            receiveRemindTime = "5";
            receiveRemind = "mins";
        }

        reminder = receiveRemindTime + " " + receiveRemind + " early";

        remindText.setText(reminder);

        setRemindBtn = findViewById(R.id.setRemindBtn);
        setRemindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarPopup.this, TaskRemindActivity.class);

                // pass values to TaskRemindActivity activity
                intent.putExtra("pRemindTime", receiveRemindTime);
                intent.putExtra("pRemind", receiveRemind);

                startActivityForResult(intent, 1);
            }
        });

        cancelBtn = findViewById(R.id.cancelBtn);
        doneBtn = findViewById(R.id.doneBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotificationChannel();

                calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                setAlarm();

                Intent intent = new Intent();

                intent.putExtra("year", Year);
                intent.putExtra("month", Month);
                intent.putExtra("day", Day);

                intent.putExtra("hour", hour);
                intent.putExtra("minute", minute);

                intent.putExtra("remindTime", receiveRemindTime);
                intent.putExtra("remind", receiveRemind);
                setResult(RESULT_OK, intent);

                finish();
            }
        });

    }


    // https://stackoverflow.com/a/40969871
    // This method is called when the TaskRemindActivity activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the TaskRemindActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                receiveRemindTime = data.getStringExtra("remindTime");
                receiveRemind = data.getStringExtra("remind");

                reminder = receiveRemindTime + " " + receiveRemind + " early";
                // Set text view with string
                remindText.setText(reminder);
            }
        }
    }

    // pop up TimePickerDialog
    public void popTime(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hourOfDay, minutes) -> {
            hour = hourOfDay;
            minute = minutes;

            timeText.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();

    }

    // set task reminder
    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, TaskRemindReceiver.class);
        intent.putExtra("aTask", aTask);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 2210,
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Reminder set Successfully", Toast.LENGTH_SHORT).show();

    }


    // create notification for task
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder";
            String description = "";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }
}
