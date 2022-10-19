package com.SecretDelta.delta.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.SecretDelta.delta.Fragments.CalendarFragment;
import com.SecretDelta.delta.Fragments.HabitFragment;
import com.SecretDelta.delta.Fragments.PomodoroFragment;
import com.SecretDelta.delta.Fragments.TaskFragment;
import com.SecretDelta.delta.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    CalendarFragment calendarFragment = new CalendarFragment();
    TaskFragment taskFragment = new TaskFragment();
    PomodoroFragment pomodoroFragment = new PomodoroFragment();
    HabitFragment habitFragment = new HabitFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                mToolbar,
                R.string.openNav,
                R.string.closeNav
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
                    return true;
                case R.id.task:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, taskFragment).commit();
                    return true;
                case R.id.pomodoro:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, pomodoroFragment).commit();
                    return true;
                case R.id.habbit:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, habitFragment).commit();
                    return true;
            }
            return false;
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}