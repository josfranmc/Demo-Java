package org.josfranmc.factory;

public class VideoFile implements IMultimediaFile {

	@Override
	public void play() {
		System.out.println("Running video file...");
	}
}
