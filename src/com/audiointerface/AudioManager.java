package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class AudioManager {


    public AudioManager() {
        this(44100, 10);
    }

    /**
     * @param samplingFrequency Sample rate of audio channel in Hz
     * @param aquisitionTime    Time to spend acquiring audio data, in ms
     */
    public AudioManager(int samplingFrequency, int aquisitionTime) {
    }

    public byte[] dataToAudio(byte[] data) {
        return new byte[0];
    }

    public byte[] audioToData(byte[] audio) {
        return new byte[0];
    }
}
