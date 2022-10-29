package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.SecretDelta.delta.Models.CalEventModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class NewCalendarEvent extends AppCompatActivity {

    private int year, month, day, hourOfDayFrom, minuteFrom, hourOfDayTo, minuteTo;
    private EditText  eventName, eventDescription;
    private TextView dateTxt, fromTime, toTime;
    private ImageButton btnDate;
    private Button btnSave;
    private LinearLayout layoutFrom, layoutTo;
    private CalEventModel calendarModel;
    DatabaseReference dbRef;


    private void clearControls() {
        eventName.setText("");
        eventDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calendar_event);

        String eventID = UUID.randomUUID().toString();

        dateTxt = findViewById(R.id.dateView);
        fromTime = findViewById(R.id.editTextTimeFrom);
        toTime = findViewById(R.id.editTextTimeTo);
        btnDate = findViewById(R.id.btnDate);
        btnSave = findViewById(R.id.btnSave);
        layoutFrom = findViewById(R.id.layoutFrom);
        layoutTo = findViewById(R.id.layoutTo);
        eventName = findViewById(R.id.editEventName);
        eventDescription = findViewById(R.id.editTextDescription);

        calendarModel = new CalEventModel();

        //DatePicker
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        NewCalendarEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                dateTxt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        //TimePickerFrom
        layoutFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                hourOfDayFrom = c.get(Calendar.HOUR_OF_DAY);
                minuteFrom = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewCalendarEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                fromTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hourOfDayFrom, minuteFrom, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        //TimePickerTo
        layoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                hourOfDayTo = c.get(Calendar.HOUR_OF_DAY);
                minuteTo = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewCalendarEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                toTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hourOfDayTo, minuteTo, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("CalendarEvent");

                try {
                    if (TextUtils.isEmpty(dateTxt.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Date", Toast.LENGTH_SHORT).show();
                    else {
                        calendarModel.setId(eventID);
                        calendarModel.setEventName(eventName.getText().toString().trim());
                        calendarModel.setDescription(eventDescription.getText().toString().trim());
                        calendarModel.setYear(year);
                        calendarModel.setMonth(month);
                        calendarModel.setDay(day);
                        calendarModel.setHourOfDayFrom(hourOfDayFrom);
                        calendarModel.setMinuteFrom(minuteFrom);
                        calendarModel.setHourOfDayTo(hourOfDayTo);
                        calendarModel.setMinuteTo(minuteTo);
                        dbRef.push().setValue(calendarModel);

                        Toast.makeText(getApplicationContext(), "Event Created!", Toast.LENGTH_SHORT).show();
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

    }

//    public void openSignUp(View view) {
//        Intent i1 = new Intent(this, SignUp.class);
//        startActivity(i1);
//    }
}