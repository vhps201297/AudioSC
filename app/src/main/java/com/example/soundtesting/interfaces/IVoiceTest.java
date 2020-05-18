package com.example.soundtesting.interfaces;

import com.example.soundtesting.fft.Complex;

public interface IVoiceTest {

    interface View{
        void showRecordingStart();
        void showRecordingStop();
        void updateGraphicOnFrequency();
        void showError(String errorMessage);
    }

    interface Presenter{
        // presenter to interactor
        void stopRecording();
        boolean isRecording();
        void listenerRecording(Listener listener);

        // presenter to view
        void updateViewRecording(Complex[] y);
        void updateViewStop();
        void errorMessage(String errorMessage);
        void addPoint(double x, double y);
        void updateViewRecording();
    }

    interface Interactor{
        void recording(Listener listener);
        void stopRecording();
        boolean isRecording();
    }

    interface Listener{
        void recording(double x, double y);
    }

}
