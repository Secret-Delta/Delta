package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.SecretDelta.delta.Models.PomoSettings;
import com.SecretDelta.delta.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PomodoroSettings extends AppCompatActivity {

    private PomoSettings pomodoroSettings;
    private EditText focusTime, breakTime;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_settings);

        focusTime = findViewById(R.id.editFocusDuration);
        breakTime = findViewById(R.id.editBreakDuration);
        Button saveSettings = findViewById(R.id.saveButton);
        Button resetSettings = findViewById(R.id.resetButton);
        ImageButton backBtn = findViewById(R.id.backButton);

        pomodoroSettings = new PomoSettings();

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("PomoSettings");

                pomodoroSettings.setPomodoroLength(focusTime.getText().toString().trim());
                pomodoroSettings.setShortBreakLength(breakTime.getText().toString().trim());

                databaseReference.push().setValue(pomodoroSettings);

                Toast.makeText(getApplicationContext(), "Settings saved", Toast.LENGTH_SHORT).show();
            }
        });

        resetSettings.setOnClickListener(v -> {
            focusTime.setText("25");
            breakTime.setText("5");
        });

        backBtn.setOnClickListener(v -> finish());
    }
}