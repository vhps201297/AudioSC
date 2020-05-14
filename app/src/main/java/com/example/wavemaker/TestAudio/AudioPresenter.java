package com.example.wavemaker.TestAudio;

import com.example.wavemaker.interfaces.EarTest;

public class AudioPresenter implements EarTest.Presenter {

    private EarTest.View viewActivity;
    private EarTest.Interactor interactor;

    AudioPresenter(EarTest.View view){
        this.viewActivity = view;
        interactor = new AudioInteractor(this);
    }

    @Override
    public void increaseFrequency() {
        //viewActivity.showFrequency(String.valueOf(interactor.getCurrentFrequency()));

    }

    @Override
    public void decreaseFrequency() {

    }

    @Override
    public void updateFrequency(double frequency) {
        viewActivity.setFrequency(frequency);
    }

    @Override
    public void getCurrentFrequency() {
        viewActivity.setFrequency(interactor.getCurrentFrequency());
    }

    @Override
    public void nextFrequency(boolean isHear) {
        interactor.nextFrequency(isHear);
    }

    @Override
    public void finishEarTest() {

    }

    @Override
    public void onErrorMessage(String error) {

    }

    @Override
    public void initTest() {
        interactor.init();
        viewActivity.setFrequency(interactor.getCurrentFrequency());
    }

    @Override
    public void nextStatus() {
        interactor.checkTestStatus();
    }

    @Override
    public int getStatusFrequency() {
        return interactor.getStatusFrequency();
    }

    @Override
    public int getEarCurrent() {
        return interactor.getEarCurrent();
    }

    @Override
    public void updateStatus(int value) {
        viewActivity.changeTestStatus(value);
    }


}
