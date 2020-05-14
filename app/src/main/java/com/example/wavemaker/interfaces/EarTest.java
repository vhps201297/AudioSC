package com.example.wavemaker.interfaces;

public interface EarTest {

    interface View{
        void setFrequency(double frequency);
        void showRangeFrequency(String minFreq, String maxFreq);
        void changeTestStatus(int value);
        void finishEarTest();
    }

    interface Interactor{
        void increaseFrequency();
        void decreaseFrequency();
        double getCurrentFrequency();
        void checkTestStatus();
        void nextFrequency(boolean isHear);
        int getStatusFrequency();
        int getEarCurrent();
        void init();
    }

    interface Presenter{
        void increaseFrequency();
        void decreaseFrequency();
        void updateFrequency(double frequency);
        void getCurrentFrequency();
        void nextFrequency(boolean isHear);
        void finishEarTest();
        void onErrorMessage(String error);
        void initTest();
        void nextStatus();
        int getStatusFrequency();
        int getEarCurrent();
        void updateStatus(int value);
    }
}
