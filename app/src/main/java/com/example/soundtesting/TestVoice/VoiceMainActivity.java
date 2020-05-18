package com.example.soundtesting.TestVoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.soundtesting.R;
import com.example.soundtesting.graphic.GraphicInteractor;
import com.example.soundtesting.interfaces.IVoiceTest;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class VoiceMainActivity extends AppCompatActivity implements IVoiceTest.View {

    private Button btnStartStop, btnRestart;
    private AudioRecord recorder;
    private LineChart  chartOnFrequency;
    int bufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int bytesPerElement = 2; // 2 bytes in 16bit format@Override
    private AudioRecord audioRecord;
    private int REQUEST_CODE = 5;
    private Handler handler = new Handler();
    private Thread thread;

    private VoicePresenter presenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_main);
        btnStartStop = findViewById(R.id.btn_iniciar);
        btnRestart = findViewById(R.id.btn_reiniciar);
        chartOnFrequency = findViewById(R.id.chart_frequency);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
        }

        initializeGraphic();
        presenter = new VoicePresenter(this);

    }

    public void initializeGraphic(){
        //GraphicInteractor.setXAxis();
        GraphicInteractor.init(chartOnFrequency);
        GraphicInteractor.setXAxisRealtime();
        GraphicInteractor.setYAxis(-3000,3000);
    }

    @Override
    protected void onPause() {
        if (presenter.isRecording()){
            presenter.stopRecording();
        }
        if (thread != null)
            thread.interrupt();

        super.onPause();
    }

    public void onClickStartStop(View view) {

        if (!presenter.isRecording()){
            showRecordingStart();
            updateGraphicOnFrequency();
        } else {
            presenter.stopRecording();

        }
    }

    public void onClickRestart(View view) {
        if (presenter.isRecording()){
            presenter.stopRecording();
        }else {
            Toast.makeText(this,"No se está grabando", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showRecordingStart() {
        btnStartStop.setText(R.string.str_detener);
        GraphicInteractor.init(chartOnFrequency);
        GraphicInteractor.setXAxisRealtime();
        GraphicInteractor.setYAxis(-3000,3000);
    }

    @Override
    public void showRecordingStop() {
        if (thread != null)
            thread.interrupt();
        btnStartStop.setText(R.string.str_iniciar);

    }


    @Override
    public void updateGraphicOnFrequency() {

        if (thread != null)
            thread.interrupt();

        presenter.listenerRecording(new IVoiceTest.Listener() {
            @Override
            public void recording(final double x, final double y) {

                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("update...");
                            GraphicInteractor.addAndpdateStream(x,y);
                        }
                    });
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



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
