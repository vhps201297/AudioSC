package com.example.wavemaker.TestAudio;

import com.example.wavemaker.interfaces.EarTest;

public class AudioPresenter implements EarTest.Presenter {

    private EarTest.View viewActivity;
    private EarTest.Interactor interactor;

    AudioPresenter(EarTest.View view){
        this.viewActivity = view;
        interactor = new AudioInteractor(this);
    }

    @Override
    public void increaseFrequency() {
        //viewActivity.showFrequency(String.valueOf(interactor.getCurrentFrequency()));
        interactor.increaseFrequency();

    }

    @Override
    public void decreaseFrequency() {
        interactor.decreaseFrequency();
    }

    @Override
    public void updateFrequency(double frequency) {
        viewActivity.setFrequency(frequency);
    }

    @Override
    public void getCurrentFrequency() {
        viewActivity.setFrequency(interactor.getCurrentFrequency());
    }

    @Override
    public void finishEarTest(Ear left, Ear right) {
        viewActivity.finishEarTest(left,right);
    }

    @Override
    public void initEarTest(int ear) {
        interactor.earTestInit(ear);
        viewActivity.setFrequency(interactor.getCurrentFrequency());
    }

    @Override
    public void nextStatus() {
        interactor.checkTestStatus();
    }


    //  Presenter to View

    @Override
    public int getStatusFrequency() {
        return interactor.getStatusFrequency();
    }

    /**
     * Regresa el error que se obtuvo en el test.
     * @param error mensaje de error que se obtuvo del interactor
     */
    @Override
    public void onErrorMessage(String error) {
        // manejar errores obtenidos
    }

    /**
     * Actualiza el estado en el que se encuentra el test
     * @param value bandera que determina el estado del test, puede ser:
     *             LOW_FREQUENCY  1
     *             HIGH_FREQUENCY 2
     */
    @Override
    public void updateEarStatus(int value) {
        viewActivity.showEarStatus(value);
    }

    @Override
    public void updateFreqStatus(int value) {
        viewActivity.showStatusFrequencies(value);
    }

    @Override
    public void updateTestStatus(int status) {
        viewActivity.changeTestStatus(status);
    }


}
