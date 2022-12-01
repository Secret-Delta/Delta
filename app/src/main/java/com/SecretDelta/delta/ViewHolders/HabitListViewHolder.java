package com.SecretDelta.delta.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SecretDelta.delta.Activities.DailyHabitActivity;
import com.SecretDelta.delta.Interfaces.StartActivityInterface;
import com.SecretDelta.delta.Models.Habit;
import com.SecretDelta.delta.R;


public class HabitListViewHolder extends RecyclerView.ViewHolder {

    private CheckBox checkBox;
    private TextView habitName;
    private ImageButton editHabitButton, seeReportButton;
    private Context context;
    private StartActivityInterface startActivityInterface;

    public HabitListViewHolder(@NonNull View itemView, Context context,StartActivityInterface startActivityInterface) {

        super(itemView);

        this.context=context;
        this.startActivityInterface=startActivityInterface;
        checkBox=itemView.findViewById(R.id.habit_checkbox);
        habitName=itemView.findViewById(R.id.habit_name_text);
        editHabitButton=itemView.findViewById(R.id.edit_habit_btn);
        seeReportButton=itemView.findViewById(R.id.habit_report_btn);
    }

    public void bind(Habit habit){

        habitName.setText(habit.getName());

        editHabitButton.setOnClickListener(v -> {
            startActivityInterface.startNewActivity(
                    new Intent(context, DailyHabitActivity.class)
                            .putExtra("activityType","edit")
                            .putExtra("habit",habit));



        });

        seeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
