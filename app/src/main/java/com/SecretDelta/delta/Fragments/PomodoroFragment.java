package com.SecretDelta.delta.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.SecretDelta.delta.Activities.PomodoroReports;
import com.SecretDelta.delta.Activities.PomodoroSettings;
import com.SecretDelta.delta.R;
import com.google.android.material.tabs.TabLayout;


public class PomodoroFragment extends Fragment {

    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        setHasOptionsMenu(true);
        tabLayout = view.findViewById(R.id.tabLayoutPomodoro);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoro, new FocusTimer()).commit();
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerPomodoro, new Stopwatch()).commit();
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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pomodoro_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reportPomodoroMenu) {
            Intent i = new Intent(getActivity(), PomodoroReports.class);
            this.startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.settingsPomodoroMenu) {
            Intent i = new Intent(getActivity(), PomodoroSettings.class);
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}