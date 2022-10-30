package com.SecretDelta.delta.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.SecretDelta.delta.Fragments.DailyPomodoroReport;
import com.SecretDelta.delta.Fragments.MonthlyPomodoroReport;
import com.SecretDelta.delta.Fragments.WeeklyPomodoroReport;
import com.SecretDelta.delta.Fragments.YearlyPomodoroReport;
import com.SecretDelta.delta.R;
import com.google.android.material.tabs.TabLayout;

public class PomodoroReports extends AppCompatActivity {

    private TabLayout tabLayout;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_reports);

        tabLayout = findViewById(R.id.tabLayoutPomodoroReports);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new DailyPomodoroReport()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new WeeklyPomodoroReport()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new MonthlyPomodoroReport()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoroReports, new YearlyPomodoroReport()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}