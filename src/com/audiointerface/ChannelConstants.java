package com.audiointerface;

/**
 * Created by gblea on 2016-03-06.
 */
public class ChannelConstants {
    // The maximum rate at which we can expect the receiving device to be sampling the audio input
    public static final int ANDROID_SAMPLE_RATE = 44100;

    // Maximum frequency we can expect our counterparty to be able to discern
    public static final int ANDROID_NYQUIST_RATE = ANDROID_SAMPLE_RATE / 2;

    // The amount of time we expect to be transmitting a given message for
    public static final int AQUISITION_TIME = 10;

    // Resolution of our output amplitude in bits
    public static final int RESOLUTION = 64;

}
