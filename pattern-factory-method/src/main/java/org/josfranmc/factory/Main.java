package org.josfranmc.factory;

public class Main {
	
    public static void main(String[] args) {
    	
    	System.out.println("Alternative 1:\n");
    	
    	RunnerMultimediaFile runner = new AudioFileFactory("C:\\multimedia\\audio1.mp3");
    	runner.play();
        System.out.println("---------");

    	runner = new VideoFileFactory("C:\\multimedia\\video1.avi");
    	runner.play();
        System.out.println("---------\n");
        
        System.out.println("Alternative 2:\n");
        
        IMultimediaFile file = MultimediaFileFactory.getFile(MultimediaType.AUDIO, "C:\\multimedia\\audio2.mp3");
        file.play();
        System.out.println("---------");
        
        file = MultimediaFileFactory.getFile(MultimediaType.VIDEO, "C:\\multimedia\\video2.avi");
        file.play();
        System.out.println("---------");        
    }
}