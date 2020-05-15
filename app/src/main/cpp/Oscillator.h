//
// Created by victor on 27/04/20.
//

#ifndef WAVEMAKER_OSCILLATOR_H
#define WAVEMAKER_OSCILLATOR_H
#include <atomic>
#include <stdint.h>

class Oscillator {
public:
    void setWaveOn(bool isWaveOn);
    void setSampleRate(int32_t sampleRate);
    void setSampleRate(int32_t sampleRate, double frecuency);
    void setModifySignal(int32_t sampleRate, double frecuency, double amplitude);
    void render(float *audioData, int32_t numFrames);

private:
    std::atomic<bool> isWaveOn_{false};
    double phase_ = 0.0;
    double phaseIncrement_ = 0.0;
    double frequency = 220.0;
    double amplitude = 0.3;
};


#endif //WAVEMAKER_OSCILLATOR_H


