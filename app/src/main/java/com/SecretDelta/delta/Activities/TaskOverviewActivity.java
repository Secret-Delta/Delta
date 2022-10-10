package com.SecretDelta.delta.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.SecretDelta.delta.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class TaskOverviewActivity extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_overview);

        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(v -> finish());

        barChart = findViewById(R.id.bar_chart);

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
//        barChart.getXAxis().setEnabled(false);

        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(0, 56));
        values.add(new BarEntry(1, 78));
        values.add(new BarEntry(2, 68));
        values.add(new BarEntry(3, 45));
        values.add(new BarEntry(4, 35));
        values.add(new BarEntry(5, 10));
        values.add(new BarEntry(6, 60));

        BarDataSet dataSet = new BarDataSet(values, "Days");
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

        xLabels.add("MON");
        xLabels.add("TUE");
        xLabels.add("WED");
        xLabels.add("THU");
        xLabels.add("FRI");
        xLabels.add("SAT");
        xLabels.add("SUN");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabels.get((int) value);
            }
        });


        pieChart = findViewById(R.id.pie_chart);

        ArrayList<PieEntry> pieValues = new ArrayList<>();
        int[] color = {Color.rgb(2, 128, 144), Color.rgb(204, 204, 204)};

        pieValues.add(new PieEntry(75, "Completed Tasks"));
        pieValues.add(new PieEntry(25, "Pending Tasks"));

        PieDataSet pieDataSet = new PieDataSet(pieValues, "");
        pieDataSet.setDrawValues(true);

        pieDataSet.setColors(color);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        pieDataSet.setColor(getResources().getColor(R.color.black));
        pieChart.setData(new PieData(pieDataSet));

        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);

        pieChart.animateXY(2000, 2000);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
    }
}