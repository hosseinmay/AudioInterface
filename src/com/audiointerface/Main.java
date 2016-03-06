package com.audiointerface;

import org.shared.array.ComplexArray;
import org.shared.array.RealArray;
import org.shared.fft.JavaFftService;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Main {
	static TargetDataLine microphone;
	static boolean stopCapture = false;
	static ByteArrayOutputStream byteArrayOutputStream;
	static AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

	public static void main(String[] args) {

        captureAudio();

        //AllFftTests tests = new A

        JavaFftService fftService = new JavaFftService();
        int[] dims = {1, 1};
        RealArray ftestIn = new RealArray(new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0});
        ComplexArray ftestOut;

        System.out.println(ftestIn);

        ftestOut = ftestIn.tocRe().ifft();

        System.out.println(ftestOut);

    }

	private static void captureAudio(){
		AudioFormat audioFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfo[1]); //Get the first mixer (call getMixers())
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
		System.out.println(mixerInfo[0]);
	}

    private static AudioFormat getAudioFormat(){
		float sampleRate = 8000.0F;
	    int sampleSizeInBits = 16;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = true;
	    return new AudioFormat(sampleRate,
	                           sampleSizeInBits,
	                           channels,
	                           signed,
	                           bigEndian);
    }
	static class CaptureThread extends Thread{
		byte tempBuffer[] = new byte[10000];
		public void run() {
			byteArrayOutputStream = new ByteArrayOutputStream();
			stopCapture = false;
			while (!stopCapture) {
				  int cnt = microphone.read(tempBuffer,0,tempBuffer.length);
				  if (cnt > 0) {
					  byteArrayOutputStream.write(tempBuffer,0,cnt);
				  }
				  System.out.println(byteArrayOutputStream);
			}
			try {
				byteArrayOutputStream.close();
			} catch (IOException e) {
			  	e.printStackTrace();
			}
		}
	}
}

