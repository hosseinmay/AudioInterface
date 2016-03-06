package com.audiointerface;

public class Main {
	
	public static void main(String[] args) {
        DataGrahamSocket socket = new DataGrahamSocket();
        socket.send(new byte[]{(byte) 128});
        AudioInterfaceManager manager = new AudioInterfaceManager();
		manager.captureMicrophone();
    }		
}

