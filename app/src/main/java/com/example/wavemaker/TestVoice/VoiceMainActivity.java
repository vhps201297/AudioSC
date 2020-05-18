package com.example.wavemaker.TestVoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.service.voice.VoiceInteractionService;
import android.view.View;
import android.widget.Button;

import com.example.wavemaker.R;
import com.example.wavemaker.fft.Complex;
import com.example.wavemaker.graphic.GraphicInteractor;
import com.example.wavemaker.interfaces.IVoiceTest;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.security.Permission;

public class VoiceMainActivity extends AppCompatActivity implements IVoiceTest.View {

    private Button btnStartStop, btnRestart;
    private AudioRecord recorder;
    private LineChart chartOnTime, chartOnFrequency;
    int bufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int bytesPerElement = 2; // 2 bytes in 16bit format@Override
    private AudioRecord audioRecord;
    private int REQUEST_CODE = 5;
    private Drawable iconStart, iconStop;

    private VoicePresenter presenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_main);
        btnStartStop = findViewById(R.id.btn_iniciar);
        btnRestart = findViewById(R.id.btn_reiniciar);
        chartOnTime = findViewById(R.id.chart_time);
        chartOnFrequency = findViewById(R.id.chart_frequency);
        iconStart = getBaseContext().getDrawable(R.drawable.ic_start);
        iconStop = getBaseContext().getDrawable(R.drawable.ic_pause);
        btnStartStop.setCompoundDrawablesWithIntrinsicBounds( iconStart,null,null,null);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
        }

        //GraphicInteractor.init(chartOnTime);
        GraphicInteractor.init(chartOnFrequency);
        presenter = new VoicePresenter(this, chartOnTime, chartOnFrequency);

        //GraphicInteractor.setXAxis();
        GraphicInteractor.setYAxis(-3000000,30000000);



    }

    public void onClickStartStop(View view) {

        if (!presenter.isRecording()){
            showRecordingStart();
            presenter.initRecording();
        } else {
            presenter.stopRecording();
        }
    }

    public void onClickRestart(View view) {
        presenter.stopRecording();
        showRecordingStart();
        //presenter.initRecording();
    }

    @Override
    public void showRecordingStart() {
        btnStartStop.setText(R.string.str_detener);
        btnStartStop.setCompoundDrawablesWithIntrinsicBounds( iconStop,null,null,null);
    }

    @Override
    public void showRecordingStop() {
        btnStartStop.setText(R.string.str_iniciar);
        btnStartStop.setCompoundDrawablesWithIntrinsicBounds( iconStart,null,null,null);

    }

    @Override
    public void updateGraphicOnTime() {

    }

    @Override
    public void updateGraphicOnFrequency(final Complex[] y) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("UpdateGraphicFreq");
                        GraphicInteractor.updateDateSet(y);
                    }
                });
            }
        }).start();


    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE && grantResults.length > 0 ){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED){

                new MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.app_name)
                        .setMessage("No se otorgaron los permisos")
                        .setPositiveButton(R.string.str_aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }
        }
    }
}
