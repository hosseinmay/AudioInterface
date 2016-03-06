package com.audiointerface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioInterfaceManager {
	TargetDataLine microphone;
	SourceDataLine speaker;
	boolean stopCapture = false;
	ByteArrayOutputStream microphoneStream;
	AudioFormat audioFormat;
	InputStream speakerStream;
	
	public AudioInterfaceManager() {
	
	}

	public void captureMicrophone(){
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer microphoneMixer = AudioSystem.getMixer(mixerInfo[0]);
		captureAudio(microphoneMixer);
	}
	
	public void captureMicrophone(Mixer microphoneMixer){
		captureAudio(microphoneMixer);
	}
	
	private void captureAudio(Mixer microphoneMixer){
		AudioFormat audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		try {
			microphone = (TargetDataLine) microphoneMixer.getLine(dataLineInfo);
			microphone.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		microphone.start();
		Thread captureThread = new CaptureThread();
		captureThread.start();
	}
	
	public void outputSpeaker(ByteArrayOutputStream outputStream){
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer speakerMixer = AudioSystem.getMixer(mixerInfo[0]);
		outputAudio(outputStream, speakerMixer);		
	}
	
	public void outputSpeaker(ByteArrayOutputStream outputStream, Mixer speakerMixer){
		outputAudio(outputStream, speakerMixer);
	}
	
	private void outputAudio(ByteArrayOutputStream audioOutputStream, Mixer speakerMixer){
		AudioFormat audioFormat = getAudioFormat();
		byte audioData[] = audioOutputStream.toByteArray();
		InputStream speakerStream = new ByteArrayInputStream(audioData);
		speakerStream = new AudioInputStream(speakerStream, audioFormat,
				audioData.length/audioFormat.getFrameSize());
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			speaker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speaker.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		speaker.start();
		byte tempBuffer[] = new byte[10000];
		try	{
			int cnt;
			while ((cnt=speakerStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (cnt > 0) {
					speaker.write(tempBuffer, 0, cnt);
				}
			}
	    } catch (Exception e) {
	    	System.out.println(e);
	    	System.exit(0);
	    }
		speaker.drain();
		speaker.close();
//	    Thread playThread = new PlayThread();
//	    playThread.start();
	}
			
    public static void getMixers(){
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		System.out.println("Available mixers:");
		for	(int cnt = 0; cnt < mixerInfo.length;cnt++) {
			System.out.println(mixerInfo[cnt].getName());
		}
	}
	
	public AudioFormat getAudioFormat(){
		float sampleRate = 8000.0F;
	    int sampleSizeInBits = 16;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = false;
	    return new AudioFormat(sampleRate,
	                           sampleSizeInBits,
	                           channels,
	                           signed,
	                           bigEndian);
    }
	
	class CaptureThread extends Thread {
		byte tempBuffer[] = new byte[10000];
		public void run() {
			microphoneStream = new ByteArrayOutputStream();
			while (true) {
				  int cnt = microphone.read(tempBuffer,0,tempBuffer.length);
				  if (cnt > 0) {
					  microphoneStream.write(tempBuffer,0,cnt);
				  }
			}
//			try {
//				microphoneStream.close();
//			} catch (IOException e) {
//			  	e.printStackTrace();
//			}
		}
	}
	class PlayThread extends Thread{
		byte tempBuffer[] = new byte[10000];
		public void run(){
			try	{
				int cnt;
				while ((cnt=speakerStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
					if (cnt > 0) {
						speaker.write(tempBuffer, 0, cnt);
					}
				}
		    } catch (Exception e) {
		    	System.out.println(e);
		    	System.exit(0);
		    }
			speaker.drain();
			speaker.close();
		}
	}
}
