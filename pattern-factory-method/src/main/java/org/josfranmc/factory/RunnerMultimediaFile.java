package org.josfranmc.factory;

public abstract class RunnerMultimediaFile {

	protected IMultimediaFile mf = null;
	
	public RunnerMultimediaFile(String path) {
		loadFile(path);
		mf = create();
	}
	
	public void loadFile(String path) {
		System.out.print("Loading file " + path + " -> ");
	}	
	
	protected abstract IMultimediaFile create();

	public void play() {
		mf.play();
	}
	
	public IMultimediaFile getFile() {
		return this.mf;
	}
}