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

    __android_log_print(2, "Signal amplitud", "Amplitud: %f", amplitude);
    if (!isWaveOn_.load()) phase_ = 0;

    for (int i = 0; i < numFrames; i++) {

        if (isWaveOn_.load()) {

            // Calculates the next sample value for the sine wave.
            audioData[i] = (float) (sin(phase_) * amplitude);

            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;

        } else {
            // Outputs silence by setting sample value to zero.
            audioData[i] = 0;
        }
    }
}






