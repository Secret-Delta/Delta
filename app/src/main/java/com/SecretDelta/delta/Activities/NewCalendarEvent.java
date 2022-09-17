package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.SecretDelta.delta.R;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewCalendarEvent extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();

    EditText dateTxt;
    EditText timeFrom;
    EditText timeTo;
    String eventName;
    ImageButton btnDate;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_calendar_event);

        dateTxt = (EditText) findViewById(R.id.editTextDate);
        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnSave = (Button) findViewById(R.id.btnSave);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
    }

    private void selectDate() {


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String dateString = year + " " + month + " " + date;

                Calendar eveCalendar = Calendar.getInstance();
                eveCalendar.set(Calendar.YEAR, year);
                eveCalendar.set(Calendar.MONTH, month);
                eveCalendar.set(Calendar.DAY_OF_MONTH, date);

                CharSequence dateCharSequence = DateFormat.format("EEEE, dd MMM yyyy", eveCalendar);
                dateTxt.setText(dateCharSequence);
            }
        };

    }

    public void openSignUp(View view) {
        Intent i1 = new Intent(this, SignUp.class);
        startActivity(i1);
    }
}