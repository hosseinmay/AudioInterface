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

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;  


public class Main {
	static TargetDataLine microphone;
	static SourceDataLine speaker;
	static boolean stopCapture = false;
	static ByteArrayOutputStream audioOutputStream;
	static AudioFormat audioFormat;
	static AudioInputStream audioInputStream;

	public static void main(String[] args) {
		captureAudio();
		getMixers();
	}

	private static void captureAudio(){
		AudioFormat audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfo[0]); //Get the first mixer (call getMixers())
		try {
			microphone = (TargetDataLine) mixer.getLine(dataLineInfo);
			microphone.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		microphone.start();
		Thread captureThread = new CaptureThread();
		captureThread.start();
	}
	
	public static void getMixers(){
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		System.out.println("Available mixers:");
		for	(int cnt = 0; cnt < mixerInfo.length;cnt++) {
			System.out.println(mixerInfo[cnt].getName());
		}
	}
	
	private static AudioFormat getAudioFormat(){
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
	static class CaptureThread extends Thread{
		byte tempBuffer[] = new byte[10000];
		public void run() {
			audioOutputStream = new ByteArrayOutputStream();
			int LOL = 0;
			while (LOL < 48000) {
				  int cnt = microphone.read(tempBuffer,0,tempBuffer.length);
				  LOL += cnt;
				  if (cnt > 0) {
					  audioOutputStream.write(tempBuffer,0,cnt);
				  }
				  //System.out.println(LOL);
			}
			try {
				audioOutputStream.close();
			} catch (IOException e) {
			  	e.printStackTrace();
			}
			playAudio(audioOutputStream);
		}
		private void playAudio(ByteArrayOutputStream audioOutputStream){
			byte audioData[] = audioOutputStream.toByteArray();
//			AudioData audiodata = new AudioData(audioData);
//			AudioDataStream audioStream = new AudioDataStream(audiodata);
//			AudioPlayer.player.start(audioStream);
			InputStream audioInputStream = new ByteArrayInputStream(audioData);
			AudioFormat audioFormat = getAudioFormat();
			audioInputStream = new AudioInputStream(audioInputStream, audioFormat,
					audioData.length/audioFormat.getFrameSize());
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			try {
				speaker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				speaker.open(audioFormat);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			speaker.start();
			try	{
				int cnt;
				while ((cnt=audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
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
//		    Thread playThread = new PlayThread();
//		    playThread.start();
		}
	}
	static class PlayThread extends Thread{
		byte tempBuffer[] = new byte[10000];
		public void run(){
			try	{
				int cnt;
				while ((cnt=audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
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

