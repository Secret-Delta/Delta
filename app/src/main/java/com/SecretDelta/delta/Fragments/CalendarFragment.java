package com.SecretDelta.delta.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.Activities.NewCalendarEvent;
import com.SecretDelta.delta.Adapters.EventAdapter;
import com.SecretDelta.delta.Models.CalEventModel;
import com.SecretDelta.delta.Models.TaskModel;
import com.SecretDelta.delta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarFragment extends Fragment {

    private TextView currentDate;
    Calendar calendar;
    private Boolean eventLoad = true;
    SimpleDateFormat dateFormat;
    private String date;
    FloatingActionButton button;
    private final ArrayList<CalEventModel> eventList = new ArrayList<>();


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.eventsRecyclerView)
    RecyclerView recyclerView;
    EventAdapter eventAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(requireActivity());

        if (eventLoad) {
            initEvents();
            eventLoad = false;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.eventsRecyclerView);  // get reference to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  // set layoutManger

        eventAdapter =new EventAdapter(getActivity(), eventList);       //create event adapter
        recyclerView.setAdapter(eventAdapter);

        calendar = Calendar.getInstance();
        currentDate = view.findViewById(R.id.displayCurrentDate);
        dateFormat = new SimpleDateFormat("EEEE, d MMM, yyyy");
        date = dateFormat.format(calendar.getTime());
        currentDate.setText(date);

        button = (FloatingActionButton) view.findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewEvent;
                iNewEvent = new Intent(getActivity(), NewCalendarEvent.class);
                startActivity(iNewEvent);
            }
        });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initEvents() {
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("CalendarEvent");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    CalEventModel calEventModel = dataSnap.getValue(CalEventModel.class);
                    eventList.add(calEventModel);
                    Collections.reverse(eventList);
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
            }
        });
    }
}