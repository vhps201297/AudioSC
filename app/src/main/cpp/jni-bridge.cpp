#include <jni.h>
#include <android/input.h>
#include "AudioEngine.h"

#define SILENCE 0
#define DINAMIC_FREQUENCY 2

static AudioEngine *audioEngine = new AudioEngine();

extern "C" {

JNIEXPORT void JNICALL
Java_com_example_wavemaker_TestAudio_MainActivity_touchEventSilence(JNIEnv *env, jobject obj) {
    audioEngine->setToneOn(false);
}


JNIEXPORT void JNICALL
Java_com_example_wavemaker_TestAudio_MainActivity_startEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->start();

}

JNIEXPORT void JNICALL
Java_com_example_wavemaker_TestAudio_MainActivity_newFrecuency(JNIEnv *env, jobject /* this */, jdouble newFrecuency) {
    audioEngine->setToneOn(true);
    audioEngine->setNewFrecuency(newFrecuency);
}

JNIEXPORT void JNICALL
Java_com_example_wavemaker_TestAudio_MainActivity_newSignalValue(JNIEnv *env, jobject /* this */, jdouble newFrecuency, jdouble newAmplitud) {
    audioEngine->setToneOn(true);
    audioEngine->setNewValues(newFrecuency, newAmplitud);
}

JNIEXPORT void JNICALL
Java_com_example_wavemaker_TestAudio_MainActivity_stopEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->stop();
}

JNIEXPORT void JNICALL
Java_com_example_wavemaker_TestAudio_MainActivity_restartEngine(JNIEnv *env, jobject /* this */) {
    audioEngine->restart();
}

}
