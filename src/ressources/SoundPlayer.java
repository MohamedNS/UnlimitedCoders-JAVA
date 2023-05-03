package ressources;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundPlayer {
    
    public void playSound() {
        String soundFile = "C:\\Users\\L390\\Documents\\NetBeansProjects\\healthifiedPi\\src\\ressources\\notification.mp3"; 
        Media sound = new Media(new File(soundFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        
        mediaPlayer.setOnReady(() -> {
            mediaPlayer.play();
        });
        
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
        });
    }
}
