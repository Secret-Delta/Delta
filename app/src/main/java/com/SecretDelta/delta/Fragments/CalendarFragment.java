package com.SecretDelta.delta.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SecretDelta.delta.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarFragment extends Fragment {

    TextView currentDate;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = Calendar.getInstance();
        currentDate = view.findViewById(R.id.displayCurrentDate);
        dateFormat = new SimpleDateFormat("EEEE, d MMM, yyyy");
        date = dateFormat.format(calendar.getTime());
        currentDate.setText(date);

        return view;
    }
}