package com.example.wavemaker.graphic;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class Graphic {

    private LineChart chart;

    public Graphic(LineChart chart){
        this.chart = chart;
    }

    public void setXAxis(float xMin, float xMax){
        XAxis xl = chart.getXAxis();
        //xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAxisMaximum(xMax);
        xl.setAxisMaximum(xMin);
        //xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
    }

    public void setYAxis(float yMin, float yMax){
        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(yMax);
        leftAxis.setAxisMinimum(yMin);
        leftAxis.setDrawGridLines(true);
        leftAxis.setEnabled(true);

    }
}
