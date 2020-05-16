package com.example.wavemaker.interfaces;

import com.example.wavemaker.TestAudio.Ear;

public interface EarTest {

    interface View{
        void showOverreached(int limitReached);
        void setFrequency(double frequency);
        void changeTestStatus(int value);
        void showStatusFrequencies(int statusFreq);
        void showEarStatus(int earStatus);
        void finishEarTest(Ear left, Ear right);
    }

    interface Interactor{
        void increaseFrequency();
        void decreaseFrequency();
        double getCurrentFrequency();
        void checkTestStatus();
        int getStatusFrequency();
        int getEarCurrent();
        void earTestInit(int ear);
    }

    interface Presenter{

        // presenter to interactor
        void getCurrentFrequency();
        void initEarTest(int ear);
        void nextStatus();
        int getStatusFrequency();
        void increaseFrequency();
        void decreaseFrequency();

        // presenter to view
        void updateFrequency(double frequency);
        void finishEarTest(Ear left, Ear right);
        void onErrorMessage(String error);
        void updateEarStatus(int value);
        void updateFreqStatus(int value);
        void updateTestStatus(int status);
        void audioWasOverreached(int limitedReached);
    }
}
