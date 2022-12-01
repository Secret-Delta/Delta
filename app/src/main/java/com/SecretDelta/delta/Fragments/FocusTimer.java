package com.SecretDelta.delta.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.Activities.BreakTimer;
import com.SecretDelta.delta.Activities.PomodoroSettings;
import com.SecretDelta.delta.Models.PomoSettings;
import com.SecretDelta.delta.Models.PomodoroSession;
import com.SecretDelta.delta.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FocusTimer extends Fragment implements SensorEventListener, AdapterView.OnItemSelectedListener {

    private static final long START_TIME_IN_MILLIS = 60000;

    private TextView countdownText;
    private TextView flipText;
    private Button startButton;
    private ImageView imageView;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private float rotationLeft = 360;

    private SwitchMaterial flippedSwitch, musicSwitch;
    private boolean flippedOn, musicOn;

    private SensorManager sensorManager;

    private DatabaseReference dbRef, databaseReference2;
    private PomodoroSession pomodoroSession;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_focus_timer, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerChooseFocus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.arrayChooseFocus, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("PomoSettings").child("-NFcq_TqZQBjI6caLLBI");

        //get values from database
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PomoSettings pomodoroSettings = snapshot.getValue(PomoSettings.class);
                long timerLength = (Long.parseLong(pomodoroSettings.getPomodoroLength())) * 60000;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        countdownText = v.findViewById(R.id.textViewDefaultTime);
        startButton = v.findViewById(R.id.buttonStartTimer);
        Button buttonBreak = v.findViewById(R.id.buttonBreakTimer);
        imageView = v.findViewById(R.id.ringImage);
        flipText = v.findViewById(R.id.textViewFlipMode);

        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);

        flippedSwitch = v.findViewById(R.id.switchFlipMode);
        musicSwitch = v.findViewById(R.id.switchMusic);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    stopTimer();
                }
                Intent intent = new Intent(getActivity(), BreakTimer.class);
                startActivity(intent);
            }
        });

        flippedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flippedSwitch.isChecked()) {
                    flippedOn = true;
                    startButton.setVisibility(View.INVISIBLE);
                    flipText.setVisibility(View.VISIBLE);

                } else {
                    flippedOn = false;
                    startButton.setVisibility(View.VISIBLE);
                    flipText.setVisibility(View.INVISIBLE);
                }
            }
        });

        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOn = musicSwitch.isChecked();
            }
        });

        updateCountDownText();
        return v;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                rotationLeft = 360 * ((float)timeLeftInMillis / START_TIME_IN_MILLIS);
                if(musicOn) {
                    countdownText.playSoundEffect(SoundEffectConstants.CLICK);
                }
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startButton.setText(R.string.StarButtonText);
                startButton.setBackgroundColor(getResources().getColor(R.color.candy_pink));
                timeLeftInMillis = START_TIME_IN_MILLIS;
                rotationLeft = 360;
                updateCountDownText();
                stopRotateImage(imageView);
                Toast.makeText(getContext(), "Great! You made it", Toast.LENGTH_LONG).show();

                dbRef = FirebaseDatabase.getInstance().getReference("PomodoroSession");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatter2 = new SimpleDateFormat("HH");
                Date date = new Date();
                String dateStr = formatter.format(date);
                String timeStr = formatter2.format(date);

                pomodoroSession = new PomodoroSession();
                pomodoroSession.setFocusTime(START_TIME_IN_MILLIS);
                pomodoroSession.setDate(dateStr);
                pomodoroSession.setTimes(timeStr);

                dbRef.push().setValue(pomodoroSession);

            }
        }.start();

        Toast.makeText(getContext(), "Focus on your task", Toast.LENGTH_LONG).show();
        timerRunning = true;
        rotateImage(imageView, rotationLeft, timeLeftInMillis);
        startButton.setText(R.string.PauseButtonText);
        startButton.setBackgroundColor(getResources().getColor(R.color.metallic_seaweed));

    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText(R.string.ResumeButtonText);
        startButton.setBackgroundColor(getResources().getColor(R.color.candy_pink));
        pauseRotateImage(imageView);
        Toast.makeText(getContext(), "Get back to your work", Toast.LENGTH_LONG).show();
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        stopRotateImage(imageView);
        Toast.makeText(getContext(), "You lost all your progress", Toast.LENGTH_SHORT).show();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        countdownText.setText(timeLeftFormatted);
    }

    // rotate with constant velocity
    private void rotateImage(ImageView img, float angle, long timeLeft) {
       img.animate().rotationBy(angle).setDuration(timeLeft).start();
    }

    private  void stopRotateImage(ImageView img) {
        img.animate().cancel();
        img.setRotation(0);
    }

    private void pauseRotateImage(ImageView img) {
        img.animate().cancel();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(sensorEvent);
        }
    }

    private void getAccelerometer(SensorEvent sensorEvent) {
        float[] gravity = sensorEvent.values;
        // Movement
        float x = gravity[0];
        float y = gravity[1];
        float z = gravity[2];



        if (flippedOn && !timerRunning) {
            if (y < 7.5 && z < -7.5) {
                startTimer();
            }
        }
        else if (flippedOn && timerRunning) {
            if (y > 7.5 && z > -7.5) {
                pauseTimer();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        ((TextView) view).setTextColor(getResources().getColor(R.color.white));
        ((TextView) view).setTextSize(18);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}