package com.example.wavemaker.interfaces;

import android.media.AudioRecord;

import com.example.wavemaker.fft.Complex;
import com.github.mikephil.charting.charts.LineChart;

public interface IVoiceTest {

    interface View{
        void showRecordingStart();
        void showRecordingStop();
        void updateGraphicOnTime();
        void updateGraphicOnFrequency(Complex[] y);
        void showError(String errorMessage);
    }

    interface Presenter{
        // presenter to interactor
        void initRecording();
        void stopRecording();
        boolean isRecording();

        // presenter to view
        void updateViewRecording(Complex[] y);
        void updateViewStop();
        void errorMessage(String errorMessage);
    }

    interface Interactor{
        void recording();
        void stopRecording();
        boolean isRecording();
    }

}
