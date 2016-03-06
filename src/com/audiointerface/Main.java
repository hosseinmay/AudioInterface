package com.audiointerface;

import org.shared.array.RealArray;
import org.shared.fft.JavaFftService;
import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {

		AudioInterfaceManager manager = new AudioInterfaceManager();
		manager.captureMicrophone();
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
}

