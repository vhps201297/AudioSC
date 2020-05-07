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
    void setNewFrecuency(double frecuency);

private:
    Oscillator oscillator_;
    AAudioStream *stream_;
    double frecuency;
};

#endif //WAVEMAKER_AUDIOENGINE_H
