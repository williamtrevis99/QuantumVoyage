package app;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class contains and manages the sounds used in the game.
 */

public class SoundManager {

    SoundManager() {

    }

    /**
     * The below functions declare a sound, and then pass the string adress to a
     * sperate function which plays the sound.
     * 
     * @param gunsound
     */

    protected void gunSound() {

        String gunsound = "bin/app/assets/Audio/gunfire.mp3";
        playSound(gunsound);

    }

    protected void destroySound() {

        String destroySound = "bin/app/assets/Audio/tone1.mp3";
        playSound(destroySound);

    }

    protected void killed() {

        String killed1 = "bin/app/assets/Audio/killed.mp3";
        playSound(killed1);

    }



    protected void buttonSound() {

        String buttonClick = "bin/app/assets/Audio/buttonclick.mp3";
        playSound(buttonClick);

    }

    protected void deny() {

        String deny = "bin/app/assets/Audio/deny.mp3";
        playSound(deny);

    }

    protected void levelUpSound() {

        String levelUp = "bin/app/assets/Audio/levelup.mp3";
        playSound(levelUp);

    }

    private void playSound(String sound) {
        Media hit = new Media(new File(sound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

    }

}