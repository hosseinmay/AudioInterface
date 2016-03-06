package com.audiointerface;

import org.apache.commons.lang3.ArrayUtils;
import org.shared.array.ComplexArray;
import org.shared.array.RealArray;

/**
 * Created by gblea on 2016-03-05.
 */
public class AudioByteConverter {


    private int samplingFrequency;
    private int aquisitionTime;
    private int bitsPerMessage;

    public AudioByteConverter() {
        this(ChannelConstants.NYQUIST_RATE, ChannelConstants.AQUISITION_TIME);
    }

    /**
     * @param samplingFrequency Sample rate of audio channel in Hz
     * @param aquisitionTime    Time to spend acquiring audio data, in ms
     */
    public AudioByteConverter(int samplingFrequency, int aquisitionTime) {

        this.samplingFrequency = samplingFrequency;
        this.aquisitionTime = aquisitionTime;
        this.bitsPerMessage = samplingFrequency * aquisitionTime / 1000;
    }

    public byte[] dataToAudio(byte[] data) {

        double[] frequencies = BitManipulator.extractBitsAsDoubles(data);

        // Output signal will play for some multiple of bitsPerMessage, even if we don't have enough bits to fill the last message
        double[] outputSignal = new double[(frequencies.length / bitsPerMessage + 1) * (bitsPerMessage)];

        for (int i = 0; i <= frequencies.length / bitsPerMessage; i++) {

            // Work on a block of frequencies of "bitsPerMessage" in size. Handle the edge case where we may not have a full set of frequencies
            double[] currentFrequencies = new double[bitsPerMessage];
            for (int j = 0; j < currentFrequencies.length && j + i * bitsPerMessage < frequencies.length; j++) {
                currentFrequencies[j] = frequencies[j + i * bitsPerMessage];
            }

            // First frequency (DD) = 0, next frequencies are mirrored. Eg: 0Hz 1Hz 2Hz 1Hz
            double[] fourierFrequencies = new double[bitsPerMessage * 2];
            System.arraycopy(currentFrequencies, 0, fourierFrequencies, 1, bitsPerMessage);
            ArrayUtils.reverse(currentFrequencies);
            System.arraycopy(currentFrequencies, 0, fourierFrequencies, bitsPerMessage, bitsPerMessage);

            // Construct the fourier transform to get the time domain signal
            int[] dims = {1, 1};
            RealArray fourierDomain = new RealArray(fourierFrequencies);
            ComplexArray complexTimeDomain = fourierDomain.tocRe().ifft();
            RealArray timeDomain = complexTimeDomain.torAbs();

            assert timeDomain == complexTimeDomain.torRe(); // TODO: Remove

            System.arraycopy(timeDomain.values(), 0, outputSignal, i * bitsPerMessage, bitsPerMessage);
        }

        // TODO: Convert to bytes and return
        return new byte[0];
    }

    public byte[] audioToData(byte[] audio) {

        return new byte[0];
    }
}
