package com.audiointerface;

import com.audiointerface.audio.AudioInput;

public class Main {
	
	public static void main(String[] args) {
        DataGrahamSocket socket = new DataGrahamSocket();
        byte [] bytes = {-128, 0, 0};
        socket.send(bytes);
      // AudioInput audioInput = new AudioInput(null);
      // audioInput.captureMicrophone();
    }
}

