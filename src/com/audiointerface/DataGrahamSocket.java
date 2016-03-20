package com.audiointerface;

import java.util.logging.Logger;

/**
 * Created by gblea on 2016-03-05.
 */
public class DataGrahamSocket {
    final Object lock = new Object();

    AudioByteConverter audioByteConverter = new AudioByteConverter();
    PacketChecksumManager checksumManager = new PacketChecksumManager(new NaiveChecksumGenerator());
    Logger logger = Logger.getGlobal();

    public void send(byte[] data) {

        byte [] checkedData = checksumManager.addChecksum(data);
        byte [] sizedData = PacketSizeManager.addSize(checkedData);
        long [] audioData = audioByteConverter.dataToAudio(sizedData);

        // TODO: Hossein, do something with this 'audioData'

        byte [] convertedSizedData = audioByteConverter.audioToData(audioData);
        if(PacketSizeManager.isValidSize(convertedSizedData)) {
            byte [] convertedCheckedData = PacketSizeManager.stripSize(convertedSizedData);
            if(checksumManager.isValidData(convertedCheckedData)){
                byte [] convertedData = checksumManager.stripChecksum(convertedCheckedData);
                logger.info("Successfully decoded packed");
            } else {
                logger.warning("Received data with bad checksum");
            }
        } else {
            logger.warning("Received data with missmatching size");
        }
    }

    public byte[] receive() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // TODO: Write code to piece messages back together

        return new byte[0];
    }
}