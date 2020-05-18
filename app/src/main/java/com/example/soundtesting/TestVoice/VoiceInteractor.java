package com.example.soundtesting.TestVoice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.soundtesting.fft.Complex;
import com.example.soundtesting.fft.FFT;
import com.example.soundtesting.interfaces.IVoiceTest;

public class VoiceInteractor implements IVoiceTest.Interactor {

    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferedSize = 2048;
    private AudioRecord audioRecord;
    private IVoiceTest.Presenter presenter;
    private boolean isRecord = false;
    private FFT fft;
    private Thread threadInteractor;

    VoiceInteractor(IVoiceTest.Presenter presenter){
        this.presenter = presenter;
        fft = new FFT(bufferedSize);
    }

    @Override
    public void recording(final IVoiceTest.Listener listener) {

        if (audioRecord == null){
            System.out.println("Grabando");
            isRecord = true;
            double[] x,y;
            int minBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                    RECORDER_CHANNELS, AudioFormat.ENCODING_PCM_16BIT);
            System.out.println("...minBufferSize: " + minBufferSize);
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferedSize);

            //Complex[] toTrans = new Complex[bufferedSize];
            //double[] freq = new double[bufferedSize];
            audioRecord.startRecording();
            final short[] audioBuffer = new short[bufferedSize];
            final int[] time = {0};
            Log.i("ThreadInteractor", "isRecod" + isRecord);
            threadInteractor = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRecord) {
                        int result = audioRecord.read(audioBuffer, 0, bufferedSize);
                        for (int i = 0; i < audioBuffer.length*bufferedSize; i++){
                            time[0]++;
                            //presenter.addPoint(time,audioBuffer[i]+time);
                        }
                        Log.e("InteractorVoice", "y:"+audioBuffer[0]+" x:"+ time[0]);
                        //GraphicInteractor.updateStreamTmp(time ,audioBuffer[bufferedSize/2]);
                        listener.recording((double) time[0]/10000, (double) audioBuffer[0]);
                    }
                }
            });
            threadInteractor.start();

        }



        //presenter.updateViewStop();
    }

    @Override
    public void stopRecording() {
        System.out.println("stopRecording....interactor");
        isRecord = false;
        if (threadInteractor != null){
            threadInteractor.interrupt();
        }

        if (audioRecord != null){
            audioRecord.stop();
        }

    }

    @Override
    public boolean isRecording() {
        return isRecord;
    }
}
