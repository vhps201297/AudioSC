package com.example.wavemaker.TestVoice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.wavemaker.fft.Complex;
import com.example.wavemaker.fft.FFT;
import com.example.wavemaker.graphic.GraphicInteractor;
import com.example.wavemaker.interfaces.IVoiceTest;
import com.github.mikephil.charting.charts.LineChart;

public class VoiceInteractor implements IVoiceTest.Interactor {

    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferedSize = 2048;
    private AudioRecord audioRecord;
    private IVoiceTest.Presenter presenter;
    private boolean isRecord = false;
    private FFT fft;

    VoiceInteractor(IVoiceTest.Presenter presenter){
        this.presenter = presenter;
        fft = new FFT(bufferedSize);
    }


    @Override
    public void recording() {

        System.out.println("Grabando");
        isRecord = true;
        int minBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                RECORDER_CHANNELS, AudioFormat.ENCODING_PCM_16BIT);
        System.out.println("...minBufferSize: " + minBufferSize);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferedSize * 2);

        short[] audioBuffer = new short[bufferedSize];
        Complex[] toTrans = new Complex[bufferedSize];
        //double[] freq = new double[bufferedSize];
        audioRecord.startRecording();
        while (isRecord) {
            int result = audioRecord.read(audioBuffer, 0, bufferedSize);

            for (int i = 0; i < bufferedSize; i++) {
                toTrans[i] = new Complex((float)audioBuffer[i]/Short.MAX_VALUE,0);
            }
            Complex[] g= fft.fft(toTrans); // alicando fft
            Log.e("VoiceInterctor", "y.re:" + g[0].re() + " y.im:" + g[0].im());
            presenter.updateViewRecording(g);

        }
        presenter.updateViewStop();

    }

    @Override
    public void stopRecording() {
        isRecord = false;
        audioRecord.stop();
    }

    @Override
    public boolean isRecording() {
        return isRecord;
    }
}
