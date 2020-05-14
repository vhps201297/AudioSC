package com.example.wavemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wavemaker.graphic.GraphicInteractor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }
    private native void touchEventSilence();
    private native void startEngine();
    private native void stopEngine();
    private native void newFrecuency(double newFrecuency);
    private native void newSignalValue(double newFrecuency, double newAmplitude);

    private TextView txtCenter;
    private Double currentFrecuency = 220.0;
    private LineChart lineChartGraphic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCenter = findViewById(R.id.txt_frecuency);
        lineChartGraphic = findViewById(R.id.line_chart_audio);
        //startEngine(); // se inicializa la biblioteca de oboe
        createGraphic();

    }

    @Override
    public void onDestroy() {
        //stopEngine();
        super.onDestroy();
    }

    public void onClickSilence(View view) {
        touchEventSilence();
        currentFrecuency = 440.0;
    }

    public void onClickFreq1(View view) {

        txtCenter.setText(currentFrecuency + " [HZ]");
        if (currentFrecuency > 1200.0 || currentFrecuency < 50)
            Toast.makeText(this, "Rango "+currentFrecuency+" no permitido", Toast.LENGTH_SHORT).show();
        else {
            currentFrecuency -= 100;
            newFrecuency(currentFrecuency);
        }
    }

    public void onClickFreq2(View view) {

        txtCenter.setText(currentFrecuency + " [HZ]");
        if (currentFrecuency > 1200.0 || currentFrecuency < 50)
            Toast.makeText(this, "Rango "+currentFrecuency+" no permitido", Toast.LENGTH_SHORT).show();
        else {
            currentFrecuency += 100;
            newSignalValue(currentFrecuency,0.5);
        }
    }

    public void createGraphic(){
        GraphicInteractor.init(lineChartGraphic);
        GraphicInteractor.setXAxis(0, 8500);
        GraphicInteractor.setYAxis(-50, 100);
        setInitialData();

    }

    public void setInitialData(){
        double[] frequencies = {250, 500, 1000, 2000, 4000, 6000, 8000}; // frecuencias a testear
        //double[] frequencies = {1, 2, 3, 4, 5, 6, 7}; // frecuencias a testear
        List<Entry> leftEarEntries = new ArrayList<>();
        List<Entry> rightEarEntries = new ArrayList<>();
        for (double frequency: frequencies){
            System.out.println("Frecuencia:"+frequency);
            leftEarEntries.add(new Entry((float) frequency, (float) (frequency * 0)));
            rightEarEntries.add(new Entry((float) frequency, (float) (frequency * 0)));
        }
        GraphicInteractor.addDataSet(leftEarEntries,"Oído izquierdo");
        GraphicInteractor.addDataSet(rightEarEntries, "Oído derecho");
    }
}


