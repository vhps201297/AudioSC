package com.example.wavemaker.graphic;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se tendrá acciones como un interactuador con la biblioteca MPAndroidChart
 */
public class GraphicInteractor {

    private static LineChart chart;
    private static ArrayList<Entry> entries = new ArrayList<>();
    private static int[] colors = {Color.rgb(0, 255, 0), Color.rgb(255, 0, 0)};
    public GraphicInteractor(){

    }

    public static void init(LineChart chart){
        GraphicInteractor.chart = chart;
        GraphicInteractor.chart.setDrawGridBackground(true);
        GraphicInteractor.chart.getDescription().setEnabled(false);
        LineData data = new LineData();
        data.setValueTextColor(Color.BLUE);
        GraphicInteractor.chart.setData(data);
        //GraphicInteractor.chart.setDragEnabled(true);
        //GraphicInteractor.chart.setScaleEnabled(true);
    }

    public static void setXAxis(float xMin, float xMax){
        XAxis xl = chart.getXAxis();
        //xl.setTypeface(tfLight);
        xl.setTextColor(Color.rgb(73,167,204));
        xl.setAxisMaximum(xMax);
        xl.setAxisMinimum(xMin);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xl.setTextSize(10f);
        //xl.setDrawAxisLine(true);
        //xl.setDrawGridLines(false);
        xl.enableGridDashedLine(10f, 10f, 0f);
        xl.setEnabled(true);
    }

    public static void setYAxis(float yMin, float yMax){
        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.rgb(73,167,204));
        leftAxis.setAxisMaximum(yMax);
        leftAxis.setAxisMinimum(yMin);
        leftAxis.setDrawGridLines(true);
        leftAxis.setEnabled(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        chart.getAxisRight().setEnabled(false);

    }

    public static void addDataSet(List<Entry> entries, String label){
        System.out.println("label:"+label);
        LineDataSet dataSet = new LineDataSet(entries,label);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4.5f);

        int color = colors[chart.getData().getDataSetCount() % colors.length];

        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setHighLightColor(color);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(color);

        LineData data = chart.getData();
        data.addDataSet(dataSet);
        data.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();

    }
}
