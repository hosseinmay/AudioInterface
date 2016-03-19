package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class DataGrahamSocket {
    final Object lock = new Object();

    AudioByteConverter audioByteConverter = new AudioByteConverter();
    ChecksumManager checksumManager = new ChecksumManager(new NaiveChecksumGenerator());

    public void send(byte[] data) {
        byte[] checkedData = checksumManager.generateCheckedData(data);
        byte[] audioData = audioByteConverter.dataToAudio(checkedData);
    }

    public byte[] receive() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new byte[0];
    }
}