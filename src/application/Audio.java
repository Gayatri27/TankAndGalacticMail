package application;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class Audio {

	private AudioClip clip;


	public Audio(String audioFile){
		try {
			clip = Applet.newAudioClip(new File(audioFile).toURI().toURL());
		} catch (Exception e) {
			System.out.println("Error creating audio file: " + e.getMessage());
		}
	}

	public void play() {
		clip.play();
	}
}
