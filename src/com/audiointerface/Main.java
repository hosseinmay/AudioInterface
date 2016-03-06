package com.audiointerface;

import org.shared.array.RealArray;
import org.shared.fft.JavaFftService;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
        //AllFftTests tests = new A

        JavaFftService fftService = new JavaFftService();
        int[] dims = {1, 1};
        RealArray ftestIn = new RealArray(new double[]{1.0, 1.0, 1.0, 1.0});
        double[] ftestOut = {0.0, 0.0, 0.0, 0.0};

        System.out.println(Arrays.toString(ftestIn));

        fftService.fft(dims, ftestIn, ftestOut);

        System.out.println(Arrays.toString(ftestOut));


        double[] rtestIn = ftestOut;
        double[] rtestOut = ftestOut;

        fftService.ifft(dims, rtestIn, rtestOut);

        System.out.println(Arrays.toString(rtestOut));
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
