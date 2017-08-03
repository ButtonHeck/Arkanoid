package com.buttonHeck.arkanoid.handler;

import com.buttonHeck.arkanoid.Options;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.lwjgl.openal.AL;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public abstract class AudioHandler {

    private static Sound
            batReflection,
            buttonClicked,
            buttonHovered,
            brickHit,
            screenReflection,
            ballMagnetized,
            itemSpawn,
            itemPicked,
            pistolPicked,
            shot,
            lose;

    private static Media musicFile;
    private static MediaPlayer musicPlayer;

    static {
        try {
            setupSounds();
            setupMusic();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private static void setupSounds() throws SlickException {
        batReflection = new Sound(AudioHandler.class.getResource("/audio/batReflection.ogg"));
        buttonClicked = new Sound(AudioHandler.class.getResource("/audio/buttonClicked.ogg"));
        buttonHovered = new Sound(AudioHandler.class.getResource("/audio/buttonHovered.ogg"));
        brickHit = new Sound(AudioHandler.class.getResource("/audio/brickHit.ogg"));
        itemSpawn = new Sound(AudioHandler.class.getResource("/audio/itemSpawn.ogg"));
        itemPicked = new Sound(AudioHandler.class.getResource("/audio/itemPicked.ogg"));
        pistolPicked = new Sound(AudioHandler.class.getResource("/audio/pistolPicked.ogg"));
        screenReflection = new Sound(AudioHandler.class.getResource("/audio/screenReflection.ogg"));
        ballMagnetized = new Sound(AudioHandler.class.getResource("/audio/ballMagnetized.ogg"));
        shot = new Sound(AudioHandler.class.getResource("/audio/shot.ogg"));
        lose = new Sound(AudioHandler.class.getResource("/audio/lose.ogg"));
    }

    private static void setupMusic() {
        musicFile = new Media(AudioHandler.class.getResource("/audio/music.mp3").toExternalForm());
        musicPlayer = new MediaPlayer(musicFile);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.setVolume(0.3);
    }

    public static void playMusic() {
        musicPlayer.play();
    }

    public static void pauseMusic(boolean isPaused) {
        musicPlayer.setVolume(isPaused ? 0.12 : 0.3);
    }

    public static void stopMusic() {
        musicPlayer.stop();
    }

    public static void batReflection(boolean ballShrunk) {
        if (Options.isSoundsOn()) {
            batReflection.play((float) (Math.random() * 0.2 + (ballShrunk ? 1.1 : 0.8)), 1.0f);
        }
    }

    public static void screenReflection(boolean ballShrunk) {
        if (Options.isSoundsOn())
            screenReflection.play((float) (Math.random() * 0.2 + (ballShrunk ? 1.1 : 0.8)), 0.8f);
    }

    public static void ballMagnetized() {
        if (Options.isSoundsOn())
            ballMagnetized.play(1.0f, 1.0f);
    }

    public static void buttonClicked() {
        if (Options.isSoundsOn())
            buttonClicked.play((float) (Math.random() * 0.1 + 0.9), 1.0f);
    }

    public static void buttonHovered() {
        if (Options.isSoundsOn())
            buttonHovered.play((float) (Math.random() * 0.1 + 0.9), 0.75f);
    }

    public static void brickHit() {
        if (Options.isSoundsOn())
            brickHit.play((float) (Math.random() * 0.3 + 0.8), 1.2f);
    }

    public static void itemSpawn() {
        if (Options.isSoundsOn())
            itemSpawn.play((float) (Math.random() * 0.1 + 0.9), 1.1f);
    }

    public static void itemPicked() {
        if (Options.isSoundsOn())
            itemPicked.play(1.0f, 0.6f);
    }

    public static void ballMissed() {
        if (Options.isSoundsOn())
            lose.play(1.0f, 1.0f);
    }

    public static void pistolPicked() {
        if (Options.isSoundsOn())
            pistolPicked.play();
    }

    public static void shot() {
        if (Options.isSoundsOn())
            shot.play(1.0f, 0.8f);
    }

    public static void finish() {
        stopMusic();
        AL.destroy();
    }
}
