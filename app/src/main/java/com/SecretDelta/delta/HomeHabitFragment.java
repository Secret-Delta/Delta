package com.SecretDelta.delta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SecretDelta.delta.Activities.DailyHabitActivity;
import com.SecretDelta.delta.Adapters.HabitListAdapter;
import com.SecretDelta.delta.Interfaces.StartActivityInterface;
import com.SecretDelta.delta.Models.Habit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeHabitFragment extends Fragment implements StartActivityInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView dailyHabitRecyclerView;
    private RecyclerView weeklyHabitRecyclerView;
    private RecyclerView monthlyHabitRecyclerView;
    private FloatingActionButton addNewHabitButton;
    private ArrayList<Habit> dailyHabits;
    private ArrayList<Habit> weeklyHabits;
    private ArrayList<Habit> monthlyHabits;
    private HabitListAdapter dailyHabitListAdapter;
    private HabitListAdapter weeklyHabitListAdapter;
    private HabitListAdapter monthlyHabitListAdapter;

    public HomeHabitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_habit, container, false);

        dailyHabitRecyclerView=view.findViewById(R.id.daily_habits_recycler_view);
        weeklyHabitRecyclerView=view.findViewById(R.id.weekly_habits_recycler_view);
        monthlyHabitRecyclerView=view.findViewById(R.id.monthly_habits_recycler_view);
        addNewHabitButton=view.findViewById(R.id.floating_add_btn);

        dailyHabits=new ArrayList<>();
        weeklyHabits=new ArrayList<>();
        monthlyHabits=new ArrayList<>();

        dailyHabitRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));

        weeklyHabitRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));

        monthlyHabitRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));

        loadDataFromFirebase();
         dailyHabitListAdapter=new HabitListAdapter(dailyHabits,getContext(),this);
         weeklyHabitListAdapter=new HabitListAdapter(weeklyHabits,getContext(),this);
         monthlyHabitListAdapter=new HabitListAdapter(monthlyHabits,getContext(),this);

        dailyHabitRecyclerView.setAdapter(dailyHabitListAdapter);
        weeklyHabitRecyclerView.setAdapter(weeklyHabitListAdapter);
        monthlyHabitRecyclerView.setAdapter(monthlyHabitListAdapter);

        addNewHabitButton.setOnClickListener(v -> {
         startActivity(
                 new Intent(getActivity(), DailyHabitActivity.class)
                         .putExtra("activityType","addNew")
         );
        });

        return view;
    }


    private void loadDataFromFirebase(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("Habits");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                int count=0;

                Habit habit=new Habit();
                HashMap hashMap=(HashMap) snapshot.getValue();
                Habit habit1=new Habit();
                habit1.setName(hashMap.get("name").toString());
                habit1.setFrequency(hashMap.get("frequency").toString());
//                habit1.setRemindTime(hashMap.get("remindTime").toString());
                habit1.setGoalDays(hashMap.get("goalDays").toString());


                addHabitToSpecificList(habit1);

                dailyHabitListAdapter.notifyDataSetChanged();
                monthlyHabitListAdapter.notifyDataSetChanged();
                weeklyHabitListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                dailyHabitListAdapter.notifyDataSetChanged();
                monthlyHabitListAdapter.notifyDataSetChanged();
                weeklyHabitListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                dailyHabitListAdapter.notifyDataSetChanged();
                monthlyHabitListAdapter.notifyDataSetChanged();
                weeklyHabitListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                dailyHabitListAdapter.notifyDataSetChanged();
                monthlyHabitListAdapter.notifyDataSetChanged();
                weeklyHabitListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dailyHabitListAdapter.notifyDataSetChanged();
                monthlyHabitListAdapter.notifyDataSetChanged();
                weeklyHabitListAdapter.notifyDataSetChanged();
            }
        });

    }

    private void addHabitToSpecificList(Habit habit){

        if (habit.getFrequency().equals("daily"))
            dailyHabits.add(habit);
        else if(habit.getFrequency().equals("weekly"))
            weeklyHabits.add(habit);
        else
            monthlyHabits.add(habit);
    }

    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
    }
}