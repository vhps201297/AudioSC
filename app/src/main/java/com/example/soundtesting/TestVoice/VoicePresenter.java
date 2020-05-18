package com.example.soundtesting.TestVoice;

import com.example.soundtesting.fft.Complex;
import com.example.soundtesting.interfaces.IVoiceTest;

public class VoicePresenter implements IVoiceTest.Presenter {

    private IVoiceTest.View view;
    private IVoiceTest.Interactor interactor;

    VoicePresenter(IVoiceTest.View view){
        this.view = view;
        interactor = new VoiceInteractor(this);
    }



    @Override
    public void stopRecording() {
        System.out.println("StopRecording...presenter");
        interactor.stopRecording();
        view.showRecordingStop();
    }

    @Override
    public boolean isRecording() {
        return interactor.isRecording();

    }

    @Override
    public void listenerRecording(IVoiceTest.Listener listener) {
        interactor.recording(listener);
    }

    @Override
    public void updateViewRecording(Complex[] y) {
        //view.updateGraphicOnFrequency(y);
    }

    @Override
    public void updateViewStop() {
        view.showRecordingStop();
    }

    @Override
    public void errorMessage(String errorMessage) {

    }

    @Override
    public void addPoint(double x, double y) {
        //view.addGraphicPoint(x,y);
    }

    @Override
    public void updateViewRecording() {
        view.updateGraphicOnFrequency();
    }
}
