package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public interface ChecksumGenerator {
    byte[] generateChecksum(byte[] data);

    byte[] generateChecksum(byte[] data, int size);

    int checksumSize();
}
