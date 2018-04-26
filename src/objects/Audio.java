package objects;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class Audio {

	private static String audioFile = "resources/turret.wav";

	public void play() {
		new Worker().start();
	}

	class Worker extends Thread {

		@Override
		public void run() {
			try {
				AudioClip clip = Applet.newAudioClip(new File(audioFile).toURI().toURL());
				clip.play();
			} catch (Exception e) {

			}
		}
	}
}
