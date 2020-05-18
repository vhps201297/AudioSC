package com.example.wavemaker.TestVoice;

import android.media.AudioRecord;

import com.example.wavemaker.fft.Complex;
import com.example.wavemaker.interfaces.IVoiceTest;
import com.github.mikephil.charting.charts.LineChart;

public class VoicePresenter implements IVoiceTest.Presenter {

    private IVoiceTest.View view;
    private IVoiceTest.Interactor interactor;

    VoicePresenter(IVoiceTest.View view){
        this.view = view;
        interactor = new VoiceInteractor(this);
    }

    @Override
    public void initRecording() {
        interactor.recording();
    }

    @Override
    public void stopRecording() {
        interactor.stopRecording();
        view.showRecordingStop();
    }

    @Override
    public boolean isRecording() {
        return interactor.isRecording();

    }

    @Override
    public void updateViewRecording(Complex[] y) {
        view.updateGraphicOnFrequency(y);
    }

    @Override
    public void updateViewStop() {
        view.showRecordingStop();
    }

    @Override
    public void errorMessage(String errorMessage) {

    }
}
