package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.SecretDelta.delta.R;

public class SignUp extends AppCompatActivity {

    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignup = (Button) findViewById(R.id.btnSignup);
    }

    public void openLogin(View view) {
        Intent i1 = new Intent(this, SignIn.class);
        startActivity(i1);
    }
}