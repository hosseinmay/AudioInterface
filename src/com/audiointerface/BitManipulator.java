package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class BitManipulator {
    private static final double THRESHOLD = 0.01;

    public static double[] extractBitsAsDoubles(byte[] data) {
        double[] output = new double[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            output[i * 8 + 0] = (data[i] & 128) == 0 ? 0.0 : 1.0;
            output[i * 8 + 1] = (data[i] & 64) == 0 ? 0.0 : 1.0;
            output[i * 8 + 2] = (data[i] & 32) == 0 ? 0.0 : 1.0;
            output[i * 8 + 3] = (data[i] & 16) == 0 ? 0.0 : 1.0;
            output[i * 8 + 4] = (data[i] & 8) == 0 ? 0.0 : 1.0;
            output[i * 8 + 5] = (data[i] & 4) == 0 ? 0.0 : 1.0;
            output[i * 8 + 6] = (data[i] & 2) == 0 ? 0.0 : 1.0;
            output[i * 8 + 7] = (data[i] & 1) == 0 ? 0.0 : 1.0;
        }
        return output;
    }

    public static byte [] extractDoublesAsBits(double [] data) {
        if (data.length % 8 != 0) {
            throw new IllegalArgumentException("'data' size must be a multiple of 8");
        }

        double max = 0;
        for (double datum : data) {
            if (datum > max) {
                max = datum;
            }
        }

        final double THRESHOLD = max / 10;

        byte [] output = new byte[data.length / 8];

        for (int i = 0; i < data.length; i += 8) {
            output[i/8] |= data[i + 0] > THRESHOLD ? 128 : 0;
            output[i/8] |= data[i + 1] > THRESHOLD ? 64 : 0;
            output[i/8] |= data[i + 2] > THRESHOLD ? 32 : 0;
            output[i/8] |= data[i + 3] > THRESHOLD ? 16 : 0;
            output[i/8] |= data[i + 4] > THRESHOLD ? 8 : 0;
            output[i/8] |= data[i + 5] > THRESHOLD ? 4 : 0;
            output[i/8] |= data[i + 6] > THRESHOLD ? 2 : 0;
            output[i/8] |= data[i + 7] > THRESHOLD ? 1 : 0;
        }

        return output;
    }
}
