package com.example.wavemaker.TestAudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.wavemaker.TestAudio.AudioInteractor.HIGH_FREQUENCY;
import static com.example.wavemaker.TestAudio.AudioInteractor.LOW_FREQUENCY;
import static com.example.wavemaker.TestAudio.AudioInteractor.MINIMUM_FREQUENCY;

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

    private TextView txtEarStatus, txtFreqStatus, txtIndicacionBtn;
    private TextView txtFreq;
    private ProgressBar progressBar;
    private EarTest.Presenter presenter;
    public static final int LEFT_EAR = 1;
    public static final int RIGHT_EAR = 2;
    private int ear = LEFT_EAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEarStatus = findViewById(R.id.txt_ear_status);
        txtFreqStatus = findViewById(R.id.txt_freq_status);
        txtFreq = findViewById(R.id.txt_freq);
        txtIndicacionBtn = findViewById(R.id.txt_leyenda_ind);
        progressBar = findViewById(R.id.progress_audio);
        startEngine(); // se inicializa la biblioteca de oboe
        //createGraphic();
        presenter = new AudioPresenter(this); // instancia de presentador
        presenter.initEarTest(LEFT_EAR);

    }

    @Override
    protected void onPause() {
        System.out.println("....onPause");
        stopEngine();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        System.out.println("....onRestart");

        restartEngine();
        //startEngine();
        presenter.getCurrentFrequency();
        super.onRestart();
    }


    public void onClickListo(View view) {
        presenter.nextStatus();
    }

    public void onClickPuedeOirse(View view) {
        if (presenter.getStatusFrequency() == LOW_FREQUENCY){
            presenter.decreaseFrequency();
        } else {
            presenter.increaseFrequency();
        }
    }

    public void onClickNoPuedeOirse(View view) {
        if (presenter.getStatusFrequency() == LOW_FREQUENCY)
            presenter.increaseFrequency();
        else
            presenter.decreaseFrequency();
    }

    @Override
    public void setFrequency(double frequency) {
        System.out.println("setFrecuencia:" + frequency);
        txtFreq.setText(getString(R.string.str_format_frequency, String.valueOf(frequency)));
        newFrecuency(frequency);
    }

    @Override
    public void changeTestStatus(int value) {
        progressBar.setProgress(value, true);
    }

    @Override
    public void showStatusFrequencies(int statusFreq) {
        if (statusFreq == LOW_FREQUENCY) {
            txtFreqStatus.setText(R.string.str_baja_frecuencia);
            txtIndicacionBtn.setText(getString(R.string.str_leyenda_indicación, "mínima"));
        }else {
            txtFreqStatus.setText(R.string.str_alta_frecuencia);
            txtIndicacionBtn.setText(getString(R.string.str_leyenda_indicación, "máxima"));
        }
    }

    @Override
    public void showEarStatus(int earStatus) {
        if (earStatus == LEFT_EAR)
            txtEarStatus.setText(getString(R.string.str_oido_izquierdo));
        else
            txtEarStatus.setText(getString(R.string.str_oido_derecho));
    }

    @Override
    public void showOverreached(int limitReached) {
        if (limitReached == MINIMUM_FREQUENCY){
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.str_msj_limite_minimo)
                    .setPositiveButton("Entendido", null)
                    .show();
        } else {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.str_msj_limite_maximo)
                    .setPositiveButton("Entendido",null)
                    .show();
        }
    }


    @Override
    public void finishEarTest(Ear left, Ear right) {
        // mostrar datos
        txtFreq.setText("Listo");
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("left_ear", left);
        intent.putExtra("right_ear", right);
        startActivity(intent);
        finish();
    }


}


