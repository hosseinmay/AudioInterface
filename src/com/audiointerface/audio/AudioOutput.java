package com.audiointerface.audio;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioOutput {

	SourceDataLine speaker;
	Mixer speakerMixer = null;
	InputStream speakerStream;
	AudioFormat audioFormat;
	Thread playThread;

	public AudioOutput(Mixer speakerMixer) {
		if (speakerMixer == null) {
			getMixers();
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			this.speakerMixer = AudioSystem.getMixer(mixerInfo[2]);
		} else {
			this.speakerMixer = speakerMixer;
		}
		audioFormat = AudioFormatHelper.getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

		try {
			speaker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speaker.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}
		speaker.start();
	}

	public void outputAudio(byte[] audioData){
		speakerStream = new ByteArrayInputStream(audioData);
		speakerStream = new AudioInputStream(speakerStream, audioFormat,
						audioData.length/audioFormat.getFrameSize());
//		if (playThread == null || playThread.getState() == Thread.State.TERMINATED) {
			playThread = new PlayThread();
			playThread.start();
//		} else {
//			Logger.getAnonymousLogger().log(Level.INFO, "Thread in progress");
//		}
	}

	public static void getMixers(){
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		System.out.println("Available mixers:");
		for (Mixer.Info aMixerInfo : mixerInfo) {
			System.out.println(aMixerInfo.getName());
		}
	}

	class PlayThread extends Thread{
		public PlayThread() {
			this.setName("Play Thread");
		}
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
				e.printStackTrace();
				System.exit(0);
			}
			speaker.drain();
			speaker.close();
		}
	}
}
