package com.example.wavemaker.TestAudio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wavemaker.R;
import com.example.wavemaker.graphic.GraphicInteractor;
import com.example.wavemaker.interfaces.EarTest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.example.wavemaker.TestAudio.AudioInteractor.LOW_FREQUENCY;

public class MainActivity extends AppCompatActivity implements EarTest.View {

    static {
        System.loadLibrary("native-lib");
    }
    private native void touchEventSilence();
    private native void startEngine();
    private native void stopEngine();
    private native void restartEngine();
    private native void newFrecuency(double newFrecuency);
    private native void newSignalValue(double newFrecuency, double newAmplitude);

    private TextView txtStatus;
    private TextView txtFreq;
    private Double currentFrecuency = 220.0;
    private ProgressBar progressBar;
    private EarTest.Presenter presenter;
    public static final int LEFT_EAR = 1;
    public static final int RIGHT_LEFT = 2;
    private int ear = LEFT_EAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtStatus = findViewById(R.id.txt_status);
        txtFreq = findViewById(R.id.txt_freq);
        progressBar = findViewById(R.id.progress_audio);
        startEngine(); // se inicializa la biblioteca de oboe
        //createGraphic();
        presenter = new AudioPresenter(this);
        presenter.initTest();

    }

    @Override
    protected void onResume() {
        System.out.println("onResume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        System.out.println("....ONDestroy");
        //stopEngine();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        System.out.println("....ONPause");

        stopEngine();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        System.out.println("....onRestart");

        //restartEngine();
        startEngine();
        presenter.getCurrentFrequency();
        super.onRestart();
    }

    public void onClickNoPuedeOirse(View view) {
        //touchEventSilence();
        //currentFrecuency = 440.0;

        presenter.nextFrequency(false);
    }

    public void onClickApenasPuedeOirse(View view) {
        presenter.nextStatus();

    }

    public void onClickPuedeOirse(View view) {
        presenter.nextFrequency(true);

    }

    public void createGraphic(){
        //GraphicInteractor.init(lineChartGraphic);
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


    @Override
    public void setFrequency(double frequency) {
        System.out.println("setFrecuencia:" + frequency);
        txtFreq.setText(getString(R.string.str_format_frequency, (float)frequency));
        newFrecuency(frequency);
    }

    @Override
    public void showRangeFrequency(String minFreq, String maxFreq) {

    }

    @Override
    public void changeTestStatus(int value) {
        progressBar.setProgress(value, true);
    }

    @Override
    public void finishEarTest() {
        if (ear == LEFT_EAR){
            ear = RIGHT_LEFT;
        }else {
            // mostrar datos
        }
    }


}


