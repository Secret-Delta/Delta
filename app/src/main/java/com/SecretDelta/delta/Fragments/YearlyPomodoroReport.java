package com.SecretDelta.delta.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SecretDelta.delta.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class YearlyPomodoroReport extends Fragment {


    private BarChart barChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yearly_pomodoro_report, container, false);

        TextView textView = view.findViewById(R.id.totalBreakTime);

        barChart = view.findViewById(R.id.bar_chart);

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(0, 0));
        values.add(new BarEntry(1, 0));
        values.add(new BarEntry(2, 0));
        values.add(new BarEntry(3, 0));
        values.add(new BarEntry(4, 0));
        values.add(new BarEntry(5, 0));
        values.add(new BarEntry(6, 0));
        values.add(new BarEntry(7, 0));
        values.add(new BarEntry(8, 0));
        values.add(new BarEntry(9, 190));
        values.add(new BarEntry(10, 0));
        values.add(new BarEntry(11, 0));


        BarDataSet dataSet = new BarDataSet(values, "Hours");
        dataSet.setDrawValues(false);
        dataSet.setColor(getResources().getColor(R.color.metallic_seaweed));

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);

        barChart.setData(data);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        ArrayList<String> xLabels = new ArrayList<>();

        xLabels.add("Jan");
        xLabels.add("Feb");
        xLabels.add("Mar");
        xLabels.add("Apr");
        xLabels.add("May");
        xLabels.add("Jun");
        xLabels.add("Jul");
        xLabels.add("Aug");
        xLabels.add("Sep");
        xLabels.add("Oct");
        xLabels.add("Nov");
        xLabels.add("Dec");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabels.get((int) value);
            }
        });

        return view;
    }
}