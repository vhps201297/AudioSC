//
// Created by victor on 27/04/20.
//

#include "Oscillator.h"
#include <math.h>
#include <android/log.h>

#define TWO_PI (3.14159 * 2)
#define AMPLITUDE 0.3
#define FREQUENCY

void Oscillator::setSampleRate(int32_t sampleRate) {
    phaseIncrement_ = (TWO_PI * frequency) / (double) sampleRate;
}

void Oscillator::setSampleRate(int32_t sampleRate, double frecuency) {
    phaseIncrement_ = (TWO_PI * frecuency) / (double) sampleRate;
}

void Oscillator::setModifySignal(int32_t sampleRate, double frecuency, double amplitude) {
    this->frequency = frecuency;
    this->amplitude = amplitude;
    phaseIncrement_= (TWO_PI * frecuency) / (double) sampleRate;

}

void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float *audioData, int32_t numFrames) {

    if (!isWaveOn_.load()) phase_ = 0;

    for (int i = 0; i < numFrames; i++) {

        if (isWaveOn_.load()) {

            // Calculato de la siguiente onda
            audioData[i] = (float) (sin(phase_) * amplitude);

            // incrementa la fse
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;

        } else {
            audioData[i] = 0;
        }
    }
}






