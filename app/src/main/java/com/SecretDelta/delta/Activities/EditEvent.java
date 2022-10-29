package com.SecretDelta.delta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.SecretDelta.delta.Adapters.EventAdapter;
import com.SecretDelta.delta.Models.CalEventModel;
import com.SecretDelta.delta.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditEvent extends AppCompatActivity {

    private int eYear, eMonth, eDay, eHourOfDayFrom, eMinuteFrom, eHourOfDayTo, eMinuteTo;
    private int year, month, day, hourOfDayFrom, minuteFrom, hourOfDayTo, minuteTo;
    private String eEventName, eDes, eId;
    private EditText eventName, eventDescription;
    private TextView dateTxt, fromTime, toTime, eventTitle;
    private LinearLayout layoutFrom, layoutTo;
    private ImageButton btnDate, backBtn, delBtn;
    private Button btnSave;
    private CalEventModel calendarModel;
    private final ArrayList<CalEventModel> eventList = new ArrayList<>();
    DatabaseReference dbRef;

    EventAdapter eventAdapter;

    private void clearControls() {
        eventName.setText("");
        eventDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calendar_event);

        dateTxt = findViewById(R.id.dateView);
        layoutFrom = findViewById(R.id.layoutFrom);
        layoutTo = findViewById(R.id.layoutTo);
        fromTime = findViewById(R.id.editTextTimeFrom);
        toTime = findViewById(R.id.editTextTimeTo);


        btnDate = findViewById(R.id.btnDate);
        delBtn = findViewById(R.id.deleteButton);

        eventName = findViewById(R.id.editEventName);
        eventDescription = findViewById(R.id.editTextDescription);
        eventTitle = findViewById(R.id.txtEventTitle);

        calendarModel = new CalEventModel();

        eventAdapter = new EventAdapter(this, eventList);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            eId = intent.getString("eveId");
            eEventName = intent.getString("eveName");
            eDes = intent.getString("eveDes");
            eYear = intent.getInt("eveYear");
            eMonth = intent.getInt("eveMonth");
            eDay = intent.getInt("eveDay");
            eHourOfDayFrom = intent.getInt("eveHourOfDayFrom");
            eMinuteFrom = intent.getInt("eveMinuteFrom");
            eHourOfDayTo = intent.getInt("eveHourOfDayTo");
            eMinuteTo = intent.getInt("eveMinuteTo");

            eventName.setText(eEventName);
            eventDescription.setText(eDes);
            eventTitle.setText(eEventName);
        }

        delBtn = findViewById(R.id.deleteButton);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                Query query = dbRef.child("CalendarEvent").orderByChild("id").equalTo(eId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot delSnapshot: dataSnapshot.getChildren()) {
                            delSnapshot.getRef().removeValue();
                        }

                        Toast.makeText(getApplicationContext(), "Event Deleted!", Toast.LENGTH_SHORT).show();

                        Context context = view.getContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //DatePicker
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditEvent.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEvent.this,
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

                final Calendar c = Calendar.getInstance();

                hourOfDayTo = c.get(Calendar.HOUR_OF_DAY);
                minuteTo = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                toTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hourOfDayTo, minuteTo, false);
                timePickerDialog.show();
            }
        });

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("CalendarEvent");

                final String imName = eventName.getText().toString().trim();
                final String imDes = eventDescription.getText().toString().trim();

                // update database values
                Query query = dbRef.orderByChild("eventName").equalTo(eEventName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                            dataSnap.getRef().child("eventName").setValue(imName);
                            dataSnap.getRef().child("description").setValue(imDes);

                            dataSnap.getRef().child("day").setValue(eDay);
                            dataSnap.getRef().child("month").setValue(eMonth);
                            dataSnap.getRef().child("year").setValue(eYear);
                            dataSnap.getRef().child("hourOfDayFrom").setValue(eHourOfDayFrom);
                            dataSnap.getRef().child("hourOfDayTo").setValue(eHourOfDayTo);
                            dataSnap.getRef().child("minuteFrom").setValue(eMinuteFrom);
                            dataSnap.getRef().child("minuteTo").setValue(eMinuteTo);
                        }

                        Toast.makeText(EditEvent.this, "Event Updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditEvent.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}