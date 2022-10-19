package com.SecretDelta.delta.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.SecretDelta.delta.R;

public class FocusTimer extends Fragment {

    private Button breakButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_focus_timer, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinnerChooseFocus);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.arrayChooseFocus, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        breakButton = v.findViewById(R.id.buttonBreakTimer);
        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoro, new BreakTimer()).commit();
            }
        });


        return v;
    }
}