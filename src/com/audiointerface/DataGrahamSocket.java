package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class DataGrahamSocket {

    AudioByteConverter audioByteConverter = new AudioByteConverter();
    ChecksumManager checksumManager = new ChecksumManager(new NaiveChecksumGenerator());

    public void send(byte[] data) {
        byte[] checkedData = checksumManager.generateCheckedData(data);
        byte[] audioData = audioByteConverter.dataToAudio(checkedData);
    }

    public byte[] receive() {
        // TODO: Block

        return new byte[0];
    }
}
