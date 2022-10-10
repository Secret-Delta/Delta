package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.SecretDelta.delta.R;

public class SignIn extends AppCompatActivity {
Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnNext = (Button) findViewById(R.id.btnSignup);
    }

    public void openAccount(View view) {
        Intent openProfile = new Intent(this, UserProfile.class);
        startActivity(openProfile);
    }
}