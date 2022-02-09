package org.josfranmc.factory;

public class AudioFileFactory extends RunnerMultimediaFile {

	public AudioFileFactory(String path) {
		super(path);
		System.out.println("Audio");
	}

	@Override
	public IMultimediaFile create() {
		return new AudioFile();
	}
}