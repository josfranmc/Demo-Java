package org.josfranmc.factory;

public class AudioFile implements IMultimediaFile {

	@Override
	public void play() {
		System.out.println("Running audio file...");
	}

}
