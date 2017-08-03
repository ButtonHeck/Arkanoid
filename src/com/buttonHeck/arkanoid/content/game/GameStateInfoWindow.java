package com.buttonHeck.arkanoid.content.game;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.handler.TextHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.buttonHeck.arkanoid.HelperMethods.*;

class GameStateInfoWindow extends Group {

    private static final double WIDTH = Game.getGameScreenWidth();
    private static final double HEIGHT = Game.getGameScreenHeight();
    private static final double SIZE_RATIO = Game.getSizeRatio();

    private Rectangle border;
    private Text text;

    GameStateInfoWindow(String stringText) {
        border = new Rectangle(WIDTH / 2.4, HEIGHT / 4);
        border.setStrokeWidth(4);
        border.setStroke(Color.LIGHTBLUE);
        border.setArcWidth(120 * SIZE_RATIO);
        border.setArcHeight(200 * SIZE_RATIO);
        text = new Text(stringText);
        TextHandler.setup(text, TextHandler.GAME_STATE_FONT);
        getChildren().addAll(border, text);
        setXY(this, WIDTH / 2 - halfWidthOf(this), HEIGHT / 2 - halfHeightOf(this));
        setXY(text, halfWidthOf(border) - halfWidthOf(text), halfHeightOf(border) + halfHeightOf(text) - 10 * SIZE_RATIO);
        setEffect(new DropShadow(32 * SIZE_RATIO, Color.LIGHTBLUE));
        setOpacity(0.66);
    }
}
