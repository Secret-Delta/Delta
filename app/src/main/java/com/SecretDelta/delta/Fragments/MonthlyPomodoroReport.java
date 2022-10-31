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
        View view = inflater.inflate(R.layout.fragment_monthly_pomodoro_report, container, false);

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
        values.add(new BarEntry(9, 0));
        values.add(new BarEntry(10, 0));
        values.add(new BarEntry(11, 0));
        values.add(new BarEntry(12, 0));
        values.add(new BarEntry(13, 0));
        values.add(new BarEntry(14, 0));
        values.add(new BarEntry(15, 0));
        values.add(new BarEntry(16, 0));
        values.add(new BarEntry(17, 0));
        values.add(new BarEntry(18, 0));
        values.add(new BarEntry(19, 0));
        values.add(new BarEntry(20, 0));
        values.add(new BarEntry(21, 0));
        values.add(new BarEntry(22, 0));
        values.add(new BarEntry(23, 0));
        values.add(new BarEntry(24, 0));
        values.add(new BarEntry(25, 0));
        values.add(new BarEntry(26, 0));
        values.add(new BarEntry(27, 0));
        values.add(new BarEntry(28, 0));
        values.add(new BarEntry(29, 90));
        values.add(new BarEntry(30, 100));
        values.add(new BarEntry(31, 0));


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