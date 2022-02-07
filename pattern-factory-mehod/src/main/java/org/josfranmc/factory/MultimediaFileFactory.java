package org.josfranmc.factory;

public class MultimediaFileFactory {

	public static IMultimediaFile getFile(MultimediaType type, String path) {
		IMultimediaFile file = null;
		switch(type) {
			case AUDIO:
				file = (new AudioFileFactory(path)).getFile();
				break;
			case VIDEO:
				file = (new VideoFileFactory(path)).getFile();
				break;
			default:
				throw new IllegalArgumentException();
		}
		return file;
	}
}
