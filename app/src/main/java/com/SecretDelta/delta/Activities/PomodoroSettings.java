package com.SecretDelta.delta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.SecretDelta.delta.Models.PomoSettings;
import com.SecretDelta.delta.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PomodoroSettings extends AppCompatActivity {

    private PomoSettings pomodoroSettings;
    private EditText focusTime, breakTime;
    private DatabaseReference databaseReference, databaseReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_settings);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PomoSettings");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("PomoSettings").child("-NFcq_TqZQBjI6caLLBI");

        //get values from database
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pomodoroSettings = snapshot.getValue(PomoSettings.class);
                focusTime.setText(pomodoroSettings.getPomodoroLength());
                breakTime.setText(pomodoroSettings.getShortBreakLength());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PomodoroSettings.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        focusTime = findViewById(R.id.editFocusDuration);
        breakTime = findViewById(R.id.editBreakDuration);
        Button saveSettings = findViewById(R.id.saveButton);
        Button resetSettings = findViewById(R.id.resetButton);
        ImageButton backBtn = findViewById(R.id.backButton);



        pomodoroSettings = new PomoSettings();

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (focusTime.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Focus Duration", Toast.LENGTH_SHORT).show();
                } else if (breakTime.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Break Duration", Toast.LENGTH_SHORT).show();
                } else {
                    pomodoroSettings.setPomodoroLength(focusTime.getText().toString().trim());
                    pomodoroSettings.setShortBreakLength(breakTime.getText().toString().trim());
                    databaseReference.child("-NFcq_TqZQBjI6caLLBI").setValue(pomodoroSettings);
                    Toast.makeText(getApplicationContext(), "Settings saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetSettings.setOnClickListener(v -> {
            focusTime.setText("25");
            breakTime.setText("5");
            pomodoroSettings.setPomodoroLength(focusTime.getText().toString().trim());
            pomodoroSettings.setShortBreakLength(breakTime.getText().toString().trim());
            databaseReference.child("-NFcq_TqZQBjI6caLLBI").setValue(pomodoroSettings);
            Toast.makeText(getApplicationContext(), "Default values saved", Toast.LENGTH_SHORT).show();
        });

        backBtn.setOnClickListener(v -> finish());
    }
}