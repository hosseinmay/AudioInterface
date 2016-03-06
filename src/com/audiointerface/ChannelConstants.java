package com.audiointerface;

/**
 * Created by gblea on 2016-03-06.
 */
public class ChannelConstants {
    // The maximum rate at which we can expect the receiving device to be sampling the audio input
    public static final int SAMPLE_RATE = 44100;

    public static final int NYQUIST_RATE = SAMPLE_RATE / 2;

    // The amount of time we expect to be transmitting a given message for
    public static final int AQUISITION_TIME = 10;
}
