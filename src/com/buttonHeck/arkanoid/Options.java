package com.buttonHeck.arkanoid;

import com.buttonHeck.arkanoid.handler.AudioHandler;

public abstract class Options {

    private static boolean musicOn = true, soundsOn = true, itemsOn = true;

    public static void switchMusic() {
        musicOn = !musicOn;
        if (musicOn)
            AudioHandler.playMusic();
        else
            AudioHandler.stopMusic();
    }

    public static void switchSounds() {
        soundsOn = !soundsOn;
    }

    public static void switchItems() {
        itemsOn = !itemsOn;
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static boolean isSoundsOn() {
        return soundsOn;
    }

    public static boolean isItemsOn() {
        return itemsOn;
    }
}
