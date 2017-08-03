package com.buttonHeck.arkanoid.content.menu;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.content.GenericRoot;
import com.buttonHeck.arkanoid.handler.AudioHandler;
import com.buttonHeck.arkanoid.handler.ButtonHandler;
import com.buttonHeck.arkanoid.handler.TextHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.buttonHeck.arkanoid.HelperMethods.*;
import static java.lang.Math.random;

public class LevelMenuRoot extends GenericRoot {

    private final double BUTTON_WIDTH = WIDTH / 2.5, BUTTON_HEIGHT = 60 * SIZE_RATIO;
    private final Glow HOVER_FX = new Glow(0.9);
    private final DropShadow BORDER_FX = new DropShadow(20, Color.LIGHTBLUE);
    private Rectangle levelBorders[];
    private Text levelNames[];
    private Group levelButtons[];

    public LevelMenuRoot(double xOffset) {
        super(xOffset);
        levelBorders = new Rectangle[]{
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
                new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT),
        };
        for (int i = 0; i < levelBorders.length; i++) {
            Rectangle border = levelBorders[i];
            setX(border, i < 5 ? (WIDTH / 2.5 - BUTTON_WIDTH + 80 * SIZE_RATIO) : (WIDTH - BUTTON_WIDTH - 80 * SIZE_RATIO));
            setY(border, i < 5 ? (80 * SIZE_RATIO + (BUTTON_HEIGHT + 40) * i * SIZE_RATIO) : (80 * SIZE_RATIO + (BUTTON_HEIGHT + 40) * (i - 5) * SIZE_RATIO));
            border.setStrokeWidth(4);
            border.setStroke(Color.CORNFLOWERBLUE);
            border.setFill(new Color(
                    random() * 0.3 + 0.1,
                    random() * 0.3 + 0.1,
                    random() * 0.3 + 0.1,
                    1.0));
            border.setArcHeight(64 * SIZE_RATIO);
            border.setArcWidth(60 * SIZE_RATIO);
            border.setEffect(BORDER_FX);
        }
        levelNames = TextHandler.getLevelNames();
        for (int i = 0; i < levelBorders.length; i++) {
            Text name = levelNames[i];
            setX(name, xOf(levelBorders[i]) + halfWidthOf(levelBorders[i]) - halfWidthOf(name));
            setY(name, yOf(levelBorders[i]) + halfHeightOf(levelBorders[i]) + 10 * SIZE_RATIO);
        }
        levelButtons = new Group[10];
        for (int i = 0; i < levelButtons.length; i++) {
            levelButtons[i] = new Group();
            levelButtons[i].getChildren().addAll(levelBorders[i], levelNames[i]);
        }
        for (int i = 0; i < levelButtons.length; i++) {
            Group button = levelButtons[i];
            button.setOnMouseEntered(e -> {
                AudioHandler.buttonHovered();
                button.setEffect(HOVER_FX);
            });
            button.setOnMouseExited(e -> button.setEffect(null));
            button.getProperties().put("level", i);
            button.setOnMouseClicked(e -> {
                AudioHandler.buttonClicked();
                Game.startLevel((int) button.getProperties().get("level"));
            });
        }
        getChildren().addAll(levelButtons);
        getChildren().add(ButtonHandler.getLevelBackButton());
    }
}
