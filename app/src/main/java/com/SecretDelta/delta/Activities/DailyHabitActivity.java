package com.SecretDelta.delta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SecretDelta.delta.Models.Habit;
import com.SecretDelta.delta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DailyHabitActivity extends AppCompatActivity {

    private CardView dailyCardView, weeklyCardView, monthlyCardView;
    private Spinner daySpinner;
    private Button saveHabitButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Habit habitToUpload;
    private TextView habitName;
    private ImageButton deleteHabitButton;
    private String activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_habit);

        Intent receivedIntent= getIntent();
        activityType=receivedIntent.getStringExtra("activityType");



        dailyCardView=findViewById(R.id.daily_card_label);
        weeklyCardView=findViewById(R.id.weekly_card_label);
        monthlyCardView=findViewById(R.id.monthly_card_label);
        daySpinner=findViewById(R.id.days_spinner);
        saveHabitButton=findViewById(R.id.save_habit_btn);
        habitName=findViewById(R.id.habit_name_edit_text);
        deleteHabitButton=findViewById(R.id.delete_habit_btn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Habits");

        habitToUpload=new Habit();

        if(activityType.equals("edit")){
            editHabit();
        }
        else {
            addNewHabit();
        }

        String[] daysArray=new String[100];

        for (int i=0; i<100; i++)
            daysArray[i]=String.valueOf(i)+" days";

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.spinner_item,daysArray);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        daySpinner.setAdapter(arrayAdapter);

        dailyCardView.setOnClickListener(v -> {
            dailyCardView.setCardBackgroundColor(Color.parseColor("#028090"));
            habitToUpload.setFrequency("daily");
        });

        weeklyCardView.setOnClickListener(v -> {
            weeklyCardView.setCardBackgroundColor(Color.parseColor("#028090"));
            habitToUpload.setFrequency("weekly");

        });

        monthlyCardView.setOnClickListener(v -> {
            monthlyCardView.setCardBackgroundColor(Color.parseColor("#028090"));
            habitToUpload.setFrequency("monthly");

        });


    }


    private void editHabit(){

        saveHabitButton.setOnClickListener(v -> {
            Habit habit=getHabitToUpload();

            HashMap hashMap=new HashMap();
            hashMap.put("id",habit.getId());
            hashMap.put("name",habit.getName());
            hashMap.put("goalDays",habit.getGoalDays());
            hashMap.put("frequency",habit.getFrequency());

            databaseReference.child(habitName.getText().toString())
//            databaseReference.child(habit.getName())
                    .updateChildren(hashMap)
                    .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Update failed",Toast.LENGTH_SHORT).show());

        });

        deleteHabitButton.setOnClickListener(v -> {
            databaseReference.child(habitName.getText().toString()).removeValue()
                    .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"Successfully deleted",Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Delete failed",Toast.LENGTH_SHORT).show());

        });
    }

    private void addNewHabit(){

        deleteHabitButton.setVisibility(View.GONE);

        saveHabitButton.setOnClickListener(v -> {
            Habit habit=getHabitToUpload();
            databaseReference.child(habit.getName()).setValue(habit)
                    .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),"Habit successfully uploaded to firebase",Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Upload failed",Toast.LENGTH_SHORT).show());


        });
    }
    private Habit getHabitToUpload(){

        habitToUpload.setGoalDays(daySpinner.getSelectedItem().toString());
        habitToUpload.setName(habitName.getText().toString());
        habitToUpload.setId(0);
        habitToUpload.setChecked(false);
        habitToUpload.setRemindTime("remindTime");


        return habitToUpload;
    }
}