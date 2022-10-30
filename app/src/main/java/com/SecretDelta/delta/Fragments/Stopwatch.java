package com.SecretDelta.delta.Fragments;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.Activities.BreakTimer;
import com.SecretDelta.delta.R;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class Stopwatch extends Fragment implements SensorEventListener, AdapterView.OnItemSelectedListener {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private Button startButton, breakButton;

    private ImageView imageView;

    private TextView flipText;

    private SwitchMaterial flippedSwitch, musicSwitch;

    private boolean flippedOn, musicOn;

    private SensorManager sensorManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_stopwatch, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerChooseFocus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.arrayChooseFocus, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        chronometer = v.findViewById(R.id.textViewDefaultTime);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        startButton = v.findViewById(R.id.buttonStartTimer);
        breakButton = v.findViewById(R.id.buttonBreakTimer);

        imageView = v.findViewById(R.id.ringImage);

        flipText = v.findViewById(R.id.textViewFlipMode);

        flippedSwitch = v.findViewById(R.id.switchFlipMode);
        musicSwitch = v.findViewById(R.id.switchMusic);

        sensorManager = (SensorManager) getActivity().getSystemService(getContext().SENSOR_SERVICE);

        flippedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                flippedOn = true;
                startButton.setVisibility(View.INVISIBLE);
                flipText.setVisibility(View.VISIBLE);
            } else {
                flippedOn = false;
                startButton.setVisibility(View.VISIBLE);
                flipText.setVisibility(View.INVISIBLE);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    pauseChronometer();
                } else {
                    startChronometer();
                }
            }
        });

        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    stopChronometer();
                }
                Intent intent = new Intent(getActivity(), BreakTimer.class);
                startActivity(intent);
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(musicOn) {
                    chronometer.playSoundEffect(SoundEffectConstants.CLICK);
                }
            }
        });

        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOn = musicSwitch.isChecked();
            }
        });

        return v;
    }

    public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            startButton.setText("Pause Timer");
            startButton.setBackgroundColor(getResources().getColor(R.color.metallic_seaweed));
            RotateImage(imageView);
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            startButton.setText("Resume Timer");
            startButton.setBackgroundColor(getResources().getColor(R.color.candy_pink));
            pauseRotateImage(imageView);
            Toast.makeText(getContext(), "Get back to work", Toast.LENGTH_LONG).show();
        }
    }

    public void stopChronometer() {
        if(running) {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            running = false;
            startButton.setText("Start Timer");
            startButton.setBackgroundColor(getResources().getColor(R.color.candy_pink));
            StopRotateImage(imageView);
            Toast.makeText(getContext(), "End of pomodoro session", Toast.LENGTH_SHORT).show();
        }
    }

    private void RotateImage(ImageView img) {
        img.animate().rotationBy(360).setDuration(60000).start();
    }

    private void StopRotateImage(ImageView img) {
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



        if (flippedOn && !running) {
            if (y < 7.5 && z < -7.5) {
                startChronometer();
            }
        }
        else if (flippedOn && running) {
            if (y > 7.5 && z > -7.5) {
                pauseChronometer();
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