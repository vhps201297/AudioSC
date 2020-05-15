//
// Created by victor on 27/04/20.
//

#ifndef WAVEMAKER_AUDIOENGINE_H
#define WAVEMAKER_AUDIOENGINE_H


#include <aaudio/AAudio.h>
#include "Oscillator.h"

class AudioEngine {
public:
    bool start();
    void stop();
    void restart();
    void setToneOn(bool isToneOn);
    void setNewFrecuency(double frequency);
    void setNewValues(double newFrequency, double newAmplitud);

private:
    Oscillator oscillator_;
    AAudioStream *stream_;
};

#endif //WAVEMAKER_AUDIOENGINE_H
