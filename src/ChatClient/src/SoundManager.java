

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

	
	public static void playDefaultNotificationSound() {
		if(MainWindow.chckbxNewCheckBox.isSelected())
			playSound("notification.wav");
	}
	
	public static void playSound(String soundFile) {
	    try {
	    	File f = new File("./" + soundFile);
		    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioIn);
		    clip.start();
		} catch (Exception e) {e.printStackTrace();}
	}
}
