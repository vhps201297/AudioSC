package com.example.wavemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEventSilence();

    private native void startEngine();

    private native void stopEngine();

    private native void newFrecuency(double newFrecuency);

    TextView txtCenter;
    private Double currentFrecuency = 220.0;

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
}


