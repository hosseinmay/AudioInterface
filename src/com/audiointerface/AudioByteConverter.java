package com.audiointerface;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.shared.array.ComplexArray;
import org.shared.array.RealArray;

import java.util.Arrays;

/**
 * Created by gblea on 2016-03-05.
 */
public class AudioByteConverter {


    private int samplingFrequency;
    private int aquisitionTime;
    private int bitsPerMessage;

    public AudioByteConverter() {
        this(ChannelConstants.ANDROID_NYQUIST_RATE, ChannelConstants.AQUISITION_TIME);
    }

    /**
     * @param samplingFrequency Sample rate of audio channel in Hz
     * @param aquisitionTime    Time to spend acquiring audio data, in ms
     */
    public AudioByteConverter(int samplingFrequency, int aquisitionTime) {

        this.samplingFrequency = samplingFrequency;
        this.aquisitionTime = aquisitionTime;

        // TODO: Change back to a calculation
        this.bitsPerMessage = 216; //samplingFrequency * aquisitionTime / 1000;
    }

    public byte [] audioToData(long [] audio) {
        double [] doubleAudio = new double[audio.length];
        for (int i = 0; i < audio.length; i++) {
            doubleAudio[i] = audio[i];
        }

        RealArray timeDomain = new RealArray(doubleAudio);
        ComplexArray complexFreqDomain = timeDomain.tocRe().fft();
        RealArray freqDomain = complexFreqDomain.torRe();

        double [] bits = new double[bitsPerMessage];
        System.arraycopy(freqDomain.values(), 1, bits, 0, bitsPerMessage);

        return BitManipulator.extractDoublesAsBits(bits);
    }

    public long [] dataToAudio(byte[] data) {

        // TODO: How do we know the length of the message?
        double[] frequencies = BitManipulator.extractBitsAsDoubles(data);

        // Output signal will play for some multiple of bitsPerMessage, even if we don't have enough bits to fill the last message
        double[] outputSignal = new double[(frequencies.length / bitsPerMessage + 1) * bitsPerMessage * 2];

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
            RealArray timeDomain = complexTimeDomain.torRe();

            RealArray backToFourierDomain = timeDomain.tocRe().fft().torRe();

            System.out.println(timeDomain.toString());

            System.arraycopy(timeDomain.values(), 0, outputSignal, i * bitsPerMessage, bitsPerMessage * 2);
        }

        return normalizeTimeDomainDoubleToLong(outputSignal);
    }


    private long [] normalizeTimeDomainDoubleToLong(double [] doubleOutput) {
        double max = 0.0;
        for (int i = 0; i < doubleOutput.length; i++) {
            if(max < Math.abs(doubleOutput[i])){
                max = Math.abs(doubleOutput[i]);
            }
        }

        double scalingFactor = Long.MAX_VALUE/max;

        long [] longOutput = new long[doubleOutput.length];

        for (int i = 0; i < doubleOutput.length; i++) {
            longOutput[i] = (long)(doubleOutput[i] * scalingFactor);
        }

        return longOutput;
    }
}
