package com.audiointerface;

/**
 * Created by gblea on 2016-03-05.
 */
public class DataGrahamSocket {

    AudioManager audioManager = new AudioManager();
    ChecksumManager checksumManager = new ChecksumManager(new NaiveChecksumGenerator());

    public void send(byte[] data) {

    }

    public byte[] receive() {
        // TODO: Block

        return new byte[0];
    }
}
