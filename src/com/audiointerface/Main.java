package com.audiointerface;

import com.audiointerface.audio.AudioInput;

public class Main {
	
	public static void main(String[] args) {
      DataGrahamSocket socket = new DataGrahamSocket();
      socket.send(new byte[]{(byte) 128});
      AudioInput audioInput = new AudioInput(null);
      audioInput.captureMicrophone();
  }
}

