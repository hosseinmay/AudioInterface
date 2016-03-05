package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class NaiveChecksumGenerator implements ChecksumGenerator {
    @Override
    public byte[] generateChecksum(byte[] data) {
        return generateChecksum(data, data.length);
    }

    @Override
    public byte[] generateChecksum(byte[] data, int size) {
        byte output = 0;

        for (int i = 0; i < size; i++) {
            output ^= data[i];
        }

        return new byte[output];
    }

    @Override
    public int checksumSize() {
        return 1;
    }
}
