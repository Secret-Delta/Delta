package com.SecretDelta.delta.Fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.SecretDelta.delta.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

public class FocusTimer extends Fragment implements SensorEventListener {

    private static final long START_TIME_IN_MILLIS = 10000;

    private TextView countdownText;
    private TextView flipText;
    private Button startButton;
    private ImageView imageView;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private float rotationLeft = 360;

    private SwitchMaterial flippedSwitch;
    private boolean flippedOn;

    private SensorManager sensorManager;
    private float[] gravity = new float[3];
    private long lastUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_focus_timer, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerChooseFocus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.arrayChooseFocus, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        countdownText = v.findViewById(R.id.textViewDefaultTime);
        startButton = v.findViewById(R.id.buttonStartTimer);
        Button buttonBreak = v.findViewById(R.id.buttonBreakTimer);
        imageView = v.findViewById(R.id.ringImage);
        flipText = v.findViewById(R.id.textViewFlipMode);

        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        flippedSwitch = v.findViewById(R.id.switchFlipMode);
        SwitchMaterial musicSwitch = v.findViewById(R.id.switchMusic);


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

        updateCountDownText();
        return v;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                rotationLeft = 360 - imageView.getRotation();
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
            }
        }.start();

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
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        stopRotateImage(imageView);
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
        gravity = sensorEvent.values;
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






}