package com.audiointerface;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

public class Main {

	public static void main(String[] args) {
		audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
	}
	public static void getMixers(){
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		System.out.println("Available mixers:");
		for(int cnt = 0; cnt < mixerInfo.length;cnt++){
			System.out.println(mixerInfo[cnt].getName());
		}
	}
	private AudioFormat getAudioFormat(){
		float sampleRate = 22050;
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
}
