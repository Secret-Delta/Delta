package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;

import com.SecretDelta.delta.Fragments.DailyPomodoroReport;
import com.SecretDelta.delta.Fragments.MonthlyPomodoroReport;
import com.SecretDelta.delta.Fragments.WeeklyPomodoroReport;
import com.SecretDelta.delta.Fragments.YearlyPomodoroReport;
import com.SecretDelta.delta.R;
import com.google.android.material.tabs.TabLayout;

public class PomodoroReports extends AppCompatActivity {

    TabLayout tabLayout;
    FragmentContainerView fragmentContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_reports);

        tabLayout = findViewById(R.id.tabLayoutPomodoroReports);
        fragmentContainerView = findViewById(R.id.fragmentContainerPomodoroReports);

    }

    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new DailyPomodoroReport()).commit();
        }
        if (tab.getPosition() == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new WeeklyPomodoroReport()).commit();
        }
        if (tab.getPosition() == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new MonthlyPomodoroReport()).commit();
        }
        if (tab.getPosition() == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new YearlyPomodoroReport()).commit();
        }
    }
}