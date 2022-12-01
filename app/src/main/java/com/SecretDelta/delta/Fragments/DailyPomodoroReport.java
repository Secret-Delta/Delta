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


public class DailyPomodoroReport extends Fragment {

    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_pomodoro_report, container, false);

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
        values.add(new BarEntry(11, 25));
        values.add(new BarEntry(12, 25));
        values.add(new BarEntry(13, 25));
        values.add(new BarEntry(14, 25));
        values.add(new BarEntry(15, 0));
        values.add(new BarEntry(16, 0));
        values.add(new BarEntry(17, 0));
        values.add(new BarEntry(18, 0));
        values.add(new BarEntry(19, 0));
        values.add(new BarEntry(20, 0.2f));
        values.add(new BarEntry(21, 0));
        values.add(new BarEntry(22, 0));
        values.add(new BarEntry(23, 0));


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

        xLabels.add("00:00");
        xLabels.add("01:00");
        xLabels.add("02:00");
        xLabels.add("03:00");
        xLabels.add("04:00");
        xLabels.add("05:00");
        xLabels.add("06:00");
        xLabels.add("07:00");
        xLabels.add("08:00");
        xLabels.add("09:00");
        xLabels.add("10:00");
        xLabels.add("11:00");
        xLabels.add("12:00");
        xLabels.add("13:00");
        xLabels.add("14:00");
        xLabels.add("15:00");
        xLabels.add("16:00");
        xLabels.add("17:00");
        xLabels.add("18:00");
        xLabels.add("19:00");
        xLabels.add("20:00");
        xLabels.add("21:00");
        xLabels.add("22:00");
        xLabels.add("23:00");



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