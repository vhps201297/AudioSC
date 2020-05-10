package com.example.wavemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wavemaker.graphic.Graphic;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

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
        startEngine(); // se inicializa la biblioteca de oboe
        createGraphic();

    }

    @Override
    public void onDestroy() {
        stopEngine();
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
            newFrecuency(currentFrecuency);
        }
    }

    public void createGraphic(){

        Graphic graphic = new Graphic(lineChartGraphic);
        graphic.setXAxis(0, 8000);
        graphic.setYAxis(0, 100);

    }
}


