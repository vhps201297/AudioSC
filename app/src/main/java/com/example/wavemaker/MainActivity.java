package com.example.wavemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEventSilence();

    private native void startEngine();

    private native void stopEngine();

    private native void startEngineNewFrecuency(double newFrecuency);

    TextView txtCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCenter = findViewById(R.id.txt_center);
        startEngine(); // se inicializa la biblioteca de oboe
    }

    @Override
    public void onDestroy() {
        stopEngine();
        super.onDestroy();
    }

    public void onClickSilence(View view) {

        touchEventSilence();
    }

    public void onClickFreq1(View view) {
        txtCenter.setText(R.string.str_freq_1);
        startEngineNewFrecuency(440.0);
    }

    public void onClickFreq2(View view) {
        txtCenter.setText(R.string.str_freq_2);
        startEngineNewFrecuency(1000.0);
    }
}


