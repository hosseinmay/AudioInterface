package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class ChecksumManager {
    private ChecksumGenerator generator;

    public ChecksumManager(ChecksumGenerator generator) {
        this.generator = generator;
    }

    public byte[] generateCheckedData(byte[] data) {
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
}
