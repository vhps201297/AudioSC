package com.example.wavemaker.TestAudio;

import android.util.Log;

import com.example.wavemaker.interfaces.EarTest;

import java.util.HashMap;
import java.util.Map;

import static com.example.wavemaker.TestAudio.MainActivity.LEFT_EAR;
import static com.example.wavemaker.TestAudio.MainActivity.RIGHT_EAR;

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
            if (currentFrequency <= 200)
                currentFrequency += 10;
            else
                currentFrequency += 50;
        } else{
            if (currentFrequency >= 7000)
                currentFrequency += 200;
            else
                currentFrequency += 500;
        }
        presenter.updateFrequency(currentFrequency);
    }

    @Override
    public void decreaseFrequency() {
        if (statusFrequencies == LOW_FREQUENCY){
            if (currentFrequency <= 200)
                currentFrequency -= 10;
            else
                currentFrequency -= 50;
        } else {
            if (currentFrequency >= 7000)
                currentFrequency -= 200;
            else
                currentFrequency -= 500;
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
                presenter.updateTestStatus(25);
            }else {
                Log.e("WAVEMAKER", "Baja frecuencia en oido derecho:" + currentFrequency);
                rightEar.setMinFrequencyRightEar(currentFrequency);
                presenter.updateTestStatus(75);
            }
            statusFrequencies = HIGH_FREQUENCY;
            presenter.updateFreqStatus(HIGH_FREQUENCY);
            currentFrequency = frequencies.get(3);
            presenter.updateFrequency(currentFrequency);
        }else{

            if (statusEar == LEFT_EAR) {
                leftEar.setMaxFrequencyLeftEar(currentFrequency);
                statusEar = RIGHT_EAR;
                statusFrequencies = LOW_FREQUENCY;
                presenter.updateEarStatus(RIGHT_EAR);
                presenter.updateFreqStatus(LOW_FREQUENCY);
                presenter.updateTestStatus(50);
                presenter.initEarTest(RIGHT_EAR);
            }else {
                Log.e("WAVEMAKER", "Alta frecuencia en oido derecho:" + currentFrequency);
                rightEar.setMaxFrequencyRightEar(currentFrequency);
                presenter.updateTestStatus(100);
                presenter.finishEarTest(leftEar, rightEar);
            }
            currentFrequency = frequencies.get(3);
            presenter.updateFrequency(currentFrequency);
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
    public void earTestInit(int ear) {
        statusEar = ear;
        statusFrequencies = LOW_FREQUENCY;
        currentIndexFrequency = 3;
        currentFrequency = frequencies.get(currentIndexFrequency);
        presenter.updateEarStatus(ear);
        presenter.updateFreqStatus(statusFrequencies);
        presenter.updateFrequency(currentFrequency);
        if (ear == RIGHT_EAR)
            presenter.updateTestStatus(50);
    }

}
