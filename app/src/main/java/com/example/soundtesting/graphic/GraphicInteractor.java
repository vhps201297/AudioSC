package com.example.soundtesting.graphic;

import android.graphics.Color;

import com.example.soundtesting.fft.Complex;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que tendrá acciones como un interactuador con la biblioteca MPAndroidChart
 */
public class GraphicInteractor {

    private static LineChart chart;
    private static ArrayList<Entry> entries;
    private static int[] colors = {Color.rgb(0, 255, 0), Color.rgb(255, 0, 0)};
    public GraphicInteractor(){

    }

    public static void init(LineChart chart){
        entries = new ArrayList<>();
        GraphicInteractor.chart = chart;
        GraphicInteractor.chart.setDrawGridBackground(true);
        GraphicInteractor.chart.getDescription().setEnabled(false);
        LineData data = new LineData();
        data.setValueTextColor(Color.BLUE);
        GraphicInteractor.chart.setData(data);
        GraphicInteractor.chart.getData().addDataSet(createDataSet());
        GraphicInteractor.chart.setDragEnabled(true);
        GraphicInteractor.chart.setScaleEnabled(true);
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


    public static void setXAxisRealtime(){
        XAxis xl = chart.getXAxis();
        //xl.setTextColor(Color.GREEN);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawGridLines(false);
        //xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        chart.setVisibleXRangeMaximum(5000);
    }

    public static void addAndpdateStream(double x, double y){
        LineData data = chart.getData();
        data.addEntry(new Entry((float)x,(float)y),0);
        data.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.setVisibleXRangeMaximum(10000);
        chart.moveViewToX(chart.getData().getEntryCount());

    }

    public static void updateEntries(){
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.moveViewToX(chart.getData().getEntryCount());
        chart.setVisibleXRangeMaximum(10000);
    }

    public static void addEntry(double x, double y){
        LineData data = chart.getData();

        if (data != null){
            System.out.println("Agregand data");
            LineDataSet set = createDataSet();
            data.addDataSet(set);
            data.addEntry(new Entry((float)x,(float)y),0);
            //data.notifyDataChanged();
            //chart.notifyDataSetChanged();
        }else {
            System.out.println("agregando x:" + x+ " y:" + y);
            data.addEntry(new Entry((float)x,(float)y),0);
            //data.notifyDataChanged();
            //chart.notifyDataSetChanged();
        }


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

    public void addEntry(Complex g){
        LineData data = chart.getData();
        if (data != null){
            data.addEntry(new Entry((float)(g.re()),(float)(g.im())), 0);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
        } else{
            Complex[] y = new Complex[]{g};
            LineDataSet set = setDataSet(y);
            data.addDataSet(set);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
        }
    }


    public static void updateDateSet(Complex[] g){
        LineData data = chart.getData();
        if (data != null){
            System.out.println("...updateDataSet");
            LineDataSet set = setDataSet(g);
            data.addDataSet(set);
            data.notifyDataChanged();
            chart.moveViewToX(GraphicInteractor.chart.getData().getEntryCount());
            chart.notifyDataSetChanged();
        }else{
            System.out.println("...Data vacía");
        }
    }

    public static LineDataSet createDataSet(){
        LineDataSet set = new LineDataSet(entries, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        //set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        //set.setHighLightColor(Color.rgb(244, 117, 117));
        //set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        //set.setDrawValues(false);
        return set;
    }

    public static LineDataSet setDataSet(Complex[] g){
        ArrayList<Entry> entries = new ArrayList<>();
        if (g != null){
            for (int i = 0; i < g.length;i++){
                entries.add(new Entry((float)g[i].re(),(float) g[i].im()));
            }
        } else {
            entries = null;
        }
        LineDataSet set = new LineDataSet(entries, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
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
