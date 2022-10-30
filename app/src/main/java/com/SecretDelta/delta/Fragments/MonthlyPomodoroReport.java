package com.SecretDelta.delta.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SecretDelta.delta.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class MonthlyPomodoroReport extends Fragment {

    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_pomodoro_report, container, false);

        barChart = view.findViewById(R.id.bar_chart);

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(0, 56));
        values.add(new BarEntry(1, 78));
        values.add(new BarEntry(2, 68));
        values.add(new BarEntry(3, 45));
        values.add(new BarEntry(4, 35));
        values.add(new BarEntry(5, 10));
        values.add(new BarEntry(6, 60));
        values.add(new BarEntry(7, 80));
        values.add(new BarEntry(8, 70));
        values.add(new BarEntry(9, 40));
        values.add(new BarEntry(10, 30));
        values.add(new BarEntry(11, 20));
        values.add(new BarEntry(12, 50));
        values.add(new BarEntry(13, 70));
        values.add(new BarEntry(14, 60));
        values.add(new BarEntry(15, 120));
        values.add(new BarEntry(16, 30));
        values.add(new BarEntry(17, 20));
        values.add(new BarEntry(18, 50));
        values.add(new BarEntry(19, 70));
        values.add(new BarEntry(20, 60));
        values.add(new BarEntry(21, 40));
        values.add(new BarEntry(22, 30));
        values.add(new BarEntry(23, 20));
        values.add(new BarEntry(24, 50));
        values.add(new BarEntry(25, 70));
        values.add(new BarEntry(26, 60));
        values.add(new BarEntry(27, 40));
        values.add(new BarEntry(28, 30));
        values.add(new BarEntry(29, 20));
        values.add(new BarEntry(30, 50));
        values.add(new BarEntry(31, 70));


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

        xLabels.add("01");
        xLabels.add("02");
        xLabels.add("03");
        xLabels.add("04");
        xLabels.add("05");
        xLabels.add("06");
        xLabels.add("07");
        xLabels.add("08");
        xLabels.add("09");
        xLabels.add("10");
        xLabels.add("11");
        xLabels.add("12");
        xLabels.add("13");
        xLabels.add("14");
        xLabels.add("15");
        xLabels.add("16");
        xLabels.add("17");
        xLabels.add("18");
        xLabels.add("19");
        xLabels.add("20");
        xLabels.add("21");
        xLabels.add("22");
        xLabels.add("23");
        xLabels.add("24");
        xLabels.add("25");
        xLabels.add("26");
        xLabels.add("27");
        xLabels.add("28");
        xLabels.add("29");
        xLabels.add("30");
        xLabels.add("31");



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