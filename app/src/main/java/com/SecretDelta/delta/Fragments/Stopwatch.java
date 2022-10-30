package com.SecretDelta.delta.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.SecretDelta.delta.R;


public class Stopwatch extends Fragment {

    private TextView stopwatchText;
    private Button startButton;
    private Button buttonBreak;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_stopwatch, container, false);
        return v;
    }
}