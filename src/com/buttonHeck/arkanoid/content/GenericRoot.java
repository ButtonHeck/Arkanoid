package com.buttonHeck.arkanoid.content;

import com.buttonHeck.arkanoid.Game;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public abstract class GenericRoot extends Group {

    protected final double WIDTH, HEIGHT, SIZE_RATIO;
    protected final Canvas background;

    protected GenericRoot(double xOffset) {
        setX(this, xOffset);
        WIDTH = Game.getGameScreenWidth();
        HEIGHT = Game.getGameScreenHeight();
        SIZE_RATIO = Game.getSizeRatio();
        background = new Canvas(WIDTH, HEIGHT);
        background.getGraphicsContext2D().setFill(Color.BLACK);
        background.getGraphicsContext2D().fillRect(0, 0, WIDTH, HEIGHT);
        getChildren().add(background);
    }
}
