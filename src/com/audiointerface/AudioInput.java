package com.audiointerface;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class AudioInput {
	AudioFormat format = new AudioFormat(22050,16,2,true,true);
	TargetDataLine microphone;
	public void inputAudio() {
		//microphone = AudioSystem.getTargetDataLine(format);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("Line is not supported.");
		}
		try {
		    microphone = (TargetDataLine) AudioSystem.getLine(info);
		    microphone.open(format);		    
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int numBytesRead;
	        int CHUNK_SIZE = 1024;
	        byte[] data = new byte[microphone.getBufferSize() / 5];
	        microphone.start();
	        //int bytesRead = 0;
		    while(true) {
		    	numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
		    	out.write(data, 0, numBytesRead); 
		    	System.out.println(out);
		    }
		} catch (LineUnavailableException ex) {
			//Handle Error	
		}
	}
}
	