package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class PacketChecksumManager {
    private ChecksumGenerator generator;

    public PacketChecksumManager(ChecksumGenerator generator) {
        this.generator = generator;
    }

    public byte[] addChecksum(byte[] data) {
        byte[] checkedData = new byte[data.length + generator.checksumSize()];
        byte[] checksum = generator.generateChecksum(data);

        System.arraycopy(data, 0, checkedData, 0, data.length);
        System.arraycopy(checksum, 0, checkedData, data.length, checksum.length);

        return checkedData;
    }

    public boolean isValidData(byte[] checkedData) {
        int dataSize = checkedData.length - generator.checksumSize();
        byte[] checksum = generator.generateChecksum(checkedData, dataSize);
        for (int i = 0; i < checksum.length; i++) {
            if (checksum[i] != checkedData[i + dataSize]) {
                return false;
            }
        }
        return true;
    }

    public byte [] stripChecksum(byte [] data) {
        byte [] result = new byte [data.length - generator.checksumSize()];
        System.arraycopy(data, 0, result, 0, result.length);

        return result;
    }
}
