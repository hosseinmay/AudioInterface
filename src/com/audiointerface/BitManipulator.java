package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class BitManipulator {
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
}
