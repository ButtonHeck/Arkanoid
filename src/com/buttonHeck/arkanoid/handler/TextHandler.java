package com.buttonHeck.arkanoid.handler;

import com.buttonHeck.arkanoid.Game;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class TextHandler {

    public static final Font FONT, GAME_STATE_FONT;
    private static final Font LEVEL_FONT;
    private static final GaussianBlur TEXT_BLUR = new GaussianBlur(1);
    private static final double SIZE_RATIO;
    private static final Text menuButtonsTexts[], menuItemsTexts[], levelNames[], gameStartHint;

    static {
        SIZE_RATIO = Game.getSizeRatio();
        FONT = new Font("Ubuntu", 36 * SIZE_RATIO);
        GAME_STATE_FONT = new Font("Ubuntu", 60 * SIZE_RATIO);
        LEVEL_FONT = new Font("Ubuntu", 28 * SIZE_RATIO);
        menuButtonsTexts = new Text[]{
                new Text("Start"),
                new Text("Exit"),
                new Text("Back"),
                new Text("Music"),
                new Text("Sounds"),
                new Text("Items")
        };
        for (Text text : menuButtonsTexts) {
            setup(text, FONT);
        }
        menuItemsTexts = new Text[]{
                new Text("Increase paddle size"),
                new Text("Decrease paddle size"),
                new Text("Brick-thru ball"),
                new Text("+1 ball"),
                new Text("-1 ball"),
                new Text("Shrink ball"),
                new Text("Magnet paddle"),
                new Text("Increase ball speed"),
                new Text("Decrease ball speed"),
                new Text("Pistol paddle (LMB/RMB)")};
        for (Text text : menuItemsTexts) {
            setup(text, FONT);
        }
        levelNames = new Text[]{
                new Text("Stones of the Queen Age"),
                new Text("Surface annihilation"),
                new Text("RAID"),
                new Text("Sunken Temple"),
                new Text("Delicious!"),
                new Text("Sweet Heart"),
                new Text("St.Brickburg"),
                new Text("Hyperloop"),
                new Text("Soap silos"),
                new Text("???")
        };
        for (Text text : levelNames) {
            setup(text, LEVEL_FONT);
        }
        gameStartHint = new Text("Click mouse to begin");
        gameStartHint.setFill(Color.WHITE);
        gameStartHint.setFont(FONT);
        gameStartHint.setEffect(new DropShadow(10, Color.LIGHTBLUE));
    }

    public static void setup(Text text, Font font) {
        text.setFont(font);
        text.setFill(Color.WHITE);
        text.setEffect(TEXT_BLUR);
    }

    //Getters

    static Text getMenuButtonText(int index) {
        return menuButtonsTexts[index];
    }

    public static Text[] getItemDescriptions() {
        return menuItemsTexts;
    }

    public static Text[] getLevelNames() {
        return levelNames;
    }

    public static Text getStartHint() {
        return gameStartHint;
    }
}
