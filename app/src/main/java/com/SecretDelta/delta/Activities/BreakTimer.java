package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.R;

import java.util.Locale;

public class BreakTimer extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 10000;

    private TextView countdownText;
    private Button startButton;
    private ImageView imageView;
    private ImageButton backBtn;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private float rotationLeft = 360;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_timer);

        countdownText = findViewById(R.id.textViewDefaultTime);
        startButton = findViewById(R.id.buttonStartTimer);
        imageView = findViewById(R.id.ringImage);
        backBtn = findViewById(R.id.backButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    stopTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                rotationLeft = 360 * ((float)timeLeftInMillis / START_TIME_IN_MILLIS);
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startButton.setVisibility(View.INVISIBLE);
                Toast.makeText(BreakTimer.this, "Break is over! Get back to work", Toast.LENGTH_LONG).show();
            }
        }.start();

        timerRunning = true;
        startButton.setText("Stop Timer");
        rotateImage(imageView, rotationLeft, timeLeftInMillis);
        startButton.setBackgroundColor(getResources().getColor(R.color.metallic_seaweed));
    }

    private void stopTimer() {
        if(timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            stopRotateImage(imageView);
            Toast.makeText(BreakTimer.this, "Break is over! Get back to work", Toast.LENGTH_LONG).show();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        countdownText.setText(timeLeftFormatted);
    }

    private void rotateImage(ImageView img, float angle, long timeLeft) {
        img.animate().rotationBy(angle).setDuration(timeLeft).start();
    }

    private  void stopRotateImage(ImageView img) {
        img.animate().cancel();
        img.setRotation(0);
    }
}