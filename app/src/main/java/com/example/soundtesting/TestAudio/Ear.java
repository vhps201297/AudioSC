package com.example.soundtesting.TestAudio;

import java.io.Serializable;

public class Ear implements Serializable {

    private double maxFrequencyRightEar;
    private double maxFrequencyLeftEar;
    private double minFrequencyRightEar;
    private double minFrequencyLeftEar;

    Ear(){}

    Ear(double minLeft, double minRight, double maxLeft, double maxRight){
        minFrequencyLeftEar = minLeft;
        minFrequencyRightEar = minRight;
        maxFrequencyLeftEar = maxLeft;
        maxFrequencyRightEar = maxRight;

    }

    public double getMaxFrequencyRightEar() {
        return maxFrequencyRightEar;
    }

    public void setMaxFrequencyRightEar(double maxFrequencyRightEar) {
        this.maxFrequencyRightEar = maxFrequencyRightEar;
    }

    public double getMaxFrequencyLeftEar() {
        return maxFrequencyLeftEar;
    }

    public void setMaxFrequencyLeftEar(double maxFrequencyLeftEar) {
        this.maxFrequencyLeftEar = maxFrequencyLeftEar;
    }

    public double getMinFrequencyRightEar() {
        return minFrequencyRightEar;
    }

    public void setMinFrequencyRightEar(double minFrequencyRightEar) {
        this.minFrequencyRightEar = minFrequencyRightEar;
    }

    public double getMinFrequencyLeftEar() {
        return minFrequencyLeftEar;
    }

    public void setMinFrequencyLefttEar(double minFrequencyLefttEar) {
        this.minFrequencyLeftEar = minFrequencyLefttEar;
    }
}
