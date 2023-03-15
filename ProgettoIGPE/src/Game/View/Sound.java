package Game.View;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private AudioInputStream audioIn;
	private Clip clip;
	
	public Sound(String name) { 
		String percorso = "/Game/View/resources/";
		try {
			audioIn = AudioSystem.getAudioInputStream(new File(getClass().getResource(percorso+name).getPath()));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			clip = null;
			e.printStackTrace();
		}		
	}
	
	public void start() {
		if(clip != null) {
			if(clip.getFramePosition() == clip.getFrameLength())
				clip.setFramePosition(0);
			clip.start();			
		}
	}
	
	public void stop() {
		if(clip != null) {
			clip.stop();
		}
	}
	
	public void loop() { //Cosi la col. sonora non viene mai stoppata
		if(clip.getFramePosition() == clip.getFrameLength())
			clip.setFramePosition(0);
		clip.start();
	}
}