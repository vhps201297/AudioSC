package com.example.wavemaker.TestAudio;

import com.example.wavemaker.interfaces.EarTest;

import java.util.HashMap;
import java.util.Map;

import static com.example.wavemaker.TestAudio.MainActivity.LEFT_EAR;

public class AudioInteractor implements EarTest.Interactor {

    private AudioPresenter presenter;
    private double maxFrequencyRightEar;
    private double maxFrequencyLeftEar;
    private double minFrequencyRightEar;
    private double minFrequencyLeftEar;
    private int currentIndexFrequency = 3;
    private double currentFrequency;
    private Map<Integer,Double> frequencies = new HashMap<Integer, Double>(){{
        put(1, 150.0);
        put(2, 500.0);
        put(3, 1000.0);
        put(4, 2000.0);
        put(5, 4000.0);
        put(6, 6000.0);
        put(7, 8000.0); }};
    public static final int LOW_FREQUENCY = 1;
    public static final int HIGH_FREQUENCY = 2;
    private int statusFrequencies = LOW_FREQUENCY, statusEar = LEFT_EAR;
    private static final int INITIAL_HIGH_FREQUENCY = 4; // índice de frecuencias altas
    private Ear rightEar = new Ear(), leftEar = new Ear();

    AudioInteractor(AudioPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void increaseFrequency() {
        if(statusFrequencies == LOW_FREQUENCY){
            currentFrequency += 50;
        } else{
            currentFrequency += 200;
        }
        presenter.updateFrequency(currentFrequency);
    }

    @Override
    public void decreaseFrequency() {
        if (statusFrequencies == LOW_FREQUENCY){
            currentFrequency -= 50;
        } else {
            currentFrequency -= 200;
        }
        presenter.updateFrequency(currentFrequency);
    }

    @Override
    public double getCurrentFrequency() {
        return currentFrequency;
    }

    @Override
    public void checkTestStatus() {
        if(statusFrequencies == LOW_FREQUENCY){
            if (statusEar == LEFT_EAR) {
                leftEar.setMinFrequencyLefttEar(currentFrequency);
                presenter.updateStatus(25);
            }else {
                rightEar.setMinFrequencyRightEar(currentFrequency);
                presenter.updateStatus(50);
            }
        }else{
            if (statusEar == LEFT_EAR) {
                leftEar.setMaxFrequencyLeftEar(currentFrequency);
                presenter.updateStatus(75);
            }else {
                rightEar.setMaxFrequencyRightEar(currentFrequency);
                presenter.updateStatus(100);
            }
        }
    }

    @Override
    public void nextFrequency(boolean isHear) {

        switch (statusFrequencies){
            case LOW_FREQUENCY:
                handledLowFrequence(isHear);
                break;
            case  HIGH_FREQUENCY:
                handledHighFrequence(isHear);
                break;
            default:
                presenter.onErrorMessage("Estado no definido");
                break;
        }
    }

    @Override
    public int getStatusFrequency() {
        return statusFrequencies;
    }

    @Override
    public int getEarCurrent() {
        return statusEar;
    }

    @Override
    public void init() {
        statusFrequencies = LOW_FREQUENCY;
        currentIndexFrequency = 3;
        currentFrequency = frequencies.get(currentIndexFrequency);
    }


    private void handledLowFrequence(boolean isHear){
        if( currentIndexFrequency > 2){
            if(isHear){
                currentIndexFrequency -= 1;
                currentFrequency = frequencies.get(currentIndexFrequency);
            } else {
                currentFrequency += 200;
            }
            presenter.updateFrequency(currentFrequency);
        } else if(currentIndexFrequency != 1){
            if (isHear){
                if(currentFrequency <= frequencies.get(1)){
                    currentIndexFrequency = INITIAL_HIGH_FREQUENCY;
                    currentFrequency = frequencies.get(currentFrequency);
                    statusFrequencies = HIGH_FREQUENCY;
                } else{
                    currentFrequency -= 50;
                }
            } else{
                currentFrequency += 50;
            }
            presenter.updateFrequency(currentFrequency);
        }
    }

    private void handledHighFrequence(boolean isHear){
        if(statusFrequencies == HIGH_FREQUENCY  && currentIndexFrequency < (frequencies.size() - 1)){
            if(isHear){
                currentIndexFrequency += 1;
                currentFrequency = frequencies.get(currentIndexFrequency);
            } else {
                currentFrequency += 200;
            }
            presenter.updateFrequency(currentFrequency);
        } else if(statusFrequencies == HIGH_FREQUENCY && currentFrequency != frequencies.size()){
            if (isHear){
                if (currentFrequency >= frequencies.get(7)){
                    presenter.finishEarTest();
                } else {
                    currentFrequency += 200;
                }
            } else {
                currentFrequency -= 200;
            }
            presenter.updateFrequency(currentFrequency);
        }
    }
}
