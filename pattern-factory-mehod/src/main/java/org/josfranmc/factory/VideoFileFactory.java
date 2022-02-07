package org.josfranmc.factory;

public class VideoFileFactory extends RunnerMultimediaFile {

	public VideoFileFactory(String path) {
		super(path);
		System.out.println("Video");
	}

	@Override
	public IMultimediaFile create() {
		return new VideoFile();
	}
}