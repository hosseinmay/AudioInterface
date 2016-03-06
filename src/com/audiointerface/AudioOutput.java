package com.audiointerface;
import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;


public class AudioOutput {
	public AudioOutput(){
		
	};
	
	public void outputAudio(int[] byteAudioData) {
//		AudioData audiodata = new AudioData(byteAudioData);
//		AudioFormat audioFormat = getAudioFormat();
//		byte audioData[] = byteArrayOutputStream.toByteArray();
//	    InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
//		audioInputStream = new AudioInputStream(byteArrayInputStream,audioFormat,audioData.length/audioFormat.getFrameSize());
//		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
//		sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
//		sourceDataLine.open(audioFormat);
//		sourceDataLine.start();
		
	}
}
