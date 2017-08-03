package com.buttonHeck.arkanoid.handler;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.Options;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import static com.buttonHeck.arkanoid.HelperMethods.*;
import static com.buttonHeck.arkanoid.handler.TextHandler.*;
import static com.buttonHeck.arkanoid.handler.ImageHandler.*;

public class ButtonHandler {

    private static final double SIZE_RATIO;
    private static final Lighting BUTTON_FX = new Lighting(new Light.Point(120, 120, 160, Color.web("eeeeff")));
    private static final GaussianBlur BODY_FX = new GaussianBlur(24);
    private static final Scale HOVER_FX = new Scale(1.05, 1.05);
    private static final DropShadow SWITCH_ON = new DropShadow(28, Color.LAWNGREEN);
    private static final DropShadow SWITCH_OFF = new DropShadow(28, Color.RED);
    private static final Glow SWITCH_HOVER_FX = new Glow(0.9);

    private static Button menuStart, menuExit, levelBack;
    private static Switch menuItemsS, menuMusicS, menuSoundsS;

    static {
        SIZE_RATIO = Game.getSizeRatio();
        menuStart = new Button(getMenuButtonText(0), getButtonImage(false), 40, 40, e -> {
            AudioHandler.buttonClicked();
            Game.chooseLevel();
        });
        menuExit = new Button(getMenuButtonText(1), getButtonImage(false), 1040, 40, e -> {
            AudioHandler.buttonClicked();
            AudioHandler.finish();
            System.exit(0);
        });
        levelBack = new Button(getMenuButtonText(2), getButtonImage(false), 540, 640, e -> {
            AudioHandler.buttonClicked();
            Game.backToMenu();
        });
        menuMusicS = new Switch(getMenuButtonText(3), getSwitchImage(true), 360, 60, e -> {
            AudioHandler.buttonClicked();
            Options.switchMusic();
            menuMusicS.body.setImage(ImageHandler.getSwitchImage(Options.isMusicOn()));
            menuMusicS.body.setEffect(Options.isMusicOn() ? SWITCH_ON : SWITCH_OFF);
        });
        menuSoundsS = new Switch(getMenuButtonText(4), getSwitchImage(true), 570, 60, e -> {
            AudioHandler.buttonClicked();
            Options.switchSounds();
            menuSoundsS.body.setImage(ImageHandler.getSwitchImage(Options.isSoundsOn()));
            menuSoundsS.body.setEffect(Options.isSoundsOn() ? SWITCH_ON : SWITCH_OFF);
        });
        menuItemsS = new Switch(getMenuButtonText(5), getSwitchImage(true), 800, 60, e -> {
            AudioHandler.buttonClicked();
            Options.switchItems();
            menuItemsS.body.setImage(ImageHandler.getSwitchImage(Options.isItemsOn()));
            menuItemsS.body.setEffect(Options.isItemsOn() ? SWITCH_ON : SWITCH_OFF);
        });
    }

    public static Button getMenuExitButton() {
        return menuExit;
    }

    public static Button getMenuStartButton() {
        return menuStart;
    }

    public static Button getLevelBackButton() {
        return levelBack;
    }

    public static Switch[] getMenuSwitches() {
        return new Switch[]{menuMusicS, menuSoundsS, menuItemsS};
    }

    public static class Button extends Group {
        private ImageView body;
        private Text text;

        Button(Text txt, Image bodyImage, double x, double y, EventHandler<? super MouseEvent> event) {
            body = new ImageView(bodyImage);
            body.setFitWidth(bodyImage.getWidth() * SIZE_RATIO);
            body.setFitHeight(bodyImage.getHeight() * SIZE_RATIO);
            body.setEffect(BODY_FX);
            text = txt;
            getChildren().addAll(body, text);
            setXY(this, x * SIZE_RATIO, y * SIZE_RATIO);
            setX(text, xOf(body) + halfWidthOf(body) - halfWidthOf(text));
            setY(text, yOf(body) + halfHeightOf(body) + halfHeightOf(text) / 2);

            setOnMouseEntered(e -> {
                AudioHandler.buttonHovered();
                body.setImage(ImageHandler.getButtonImage(true));
                text.setFill(Color.BLACK);
                getTransforms().add(HOVER_FX);
                setX(this, xOf(this) - (widthOf(this) * 1.05 - widthOf(this)) / 2);
                setY(this, yOf(this) - (heightOf(this) * 1.05 - heightOf(this)) / 2);
            });
            setOnMouseExited(e -> {
                body.setImage(ImageHandler.getButtonImage(false));
                text.setFill(Color.WHITE);
                getTransforms().remove(HOVER_FX);
                setX(this, xOf(this) + (widthOf(this) * 1.05 - widthOf(this)) / 2);
                setY(this, yOf(this) + (heightOf(this) * 1.05 - heightOf(this)) / 2);
            });
            setOnMouseClicked(event);
            setEffect(BUTTON_FX);
        }
    }

    public static class Switch extends Group {
        private ImageView body;
        private Text text;

        Switch(Text txt, Image bodyImage, double x, double y, EventHandler<? super MouseEvent> event) {
            body = new ImageView(bodyImage);
            body.setFitHeight(bodyImage.getHeight() * SIZE_RATIO);
            body.setFitWidth(bodyImage.getWidth() * SIZE_RATIO);
            body.setEffect(SWITCH_ON);
            text = txt;
            getChildren().addAll(body, text);
            setXY(this, x * SIZE_RATIO, y * SIZE_RATIO);
            setX(text, xOf(body) + widthOf(body) + 12 * SIZE_RATIO);
            setY(text, yOf(body) + heightOf(body) - 5 * SIZE_RATIO);
            setOnMouseEntered(e -> setEffect(SWITCH_HOVER_FX));
            setOnMouseExited(e -> setEffect(null));
            setOnMouseClicked(event);
        }
    }
}
