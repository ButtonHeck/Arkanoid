package com.buttonHeck.arkanoid.content.menu;

import com.buttonHeck.arkanoid.content.GenericRoot;
import com.buttonHeck.arkanoid.handler.ButtonHandler;
import com.buttonHeck.arkanoid.handler.ImageHandler;
import com.buttonHeck.arkanoid.handler.TextHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

import static com.buttonHeck.arkanoid.HelperMethods.*;
import static java.lang.Math.random;

public class MainMenuRoot extends GenericRoot {

    private Canvas itemCanvas;
    private ImageView items[];
    private AnimatedBrick flyingBricks[];
    private Text itemInfos[];
    private DropShadow ITEM_EFFECT = new DropShadow(16, Color.WHITE);
    private AnimatedString author;
    private Timeline animation;

    public MainMenuRoot(double xOffset) {
        super(xOffset);
        initAnimatedObjects();
        initAnimation();
        initCanvas();
        initItems();
        getChildren().addAll(itemCanvas, ButtonHandler.getMenuExitButton(), ButtonHandler.getMenuStartButton());
        ButtonHandler.Switch[] switches = ButtonHandler.getMenuSwitches();
        for (ButtonHandler.Switch swc : switches)
            getChildren().add(swc);
        for (ImageView item : items)
            getChildren().add(item);
        for (Text itemInfo : itemInfos)
            getChildren().add(itemInfo);
    }

    private void initAnimatedObjects() {
        initAnimatedBricks();
        initAnimatedString();
    }

    private void initAnimatedBricks() {
        flyingBricks = new AnimatedBrick[30];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < flyingBricks.length; i++) {
            flyingBricks[i] = new AnimatedBrick(ImageHandler.getBrickImage(random.nextInt(10)),
                    random() * WIDTH,
                    random() * HEIGHT,
                    random() * 360);
            flyingBricks[i].setCacheHint(CacheHint.ROTATE);
            flyingBricks[i].setCache(true);
            getChildren().add(flyingBricks[i]);
        }
    }

    private void initAnimatedString() {
        author = new AnimatedString("ButtonHeck", random() * WIDTH, random() * HEIGHT, random() * 360);
        author.setCacheHint(CacheHint.ROTATE);
        author.setCache(true);
        getChildren().add(author);
    }

    private void initAnimation() {
        animation = new Timeline();
        for (AnimatedBrick brick : flyingBricks)
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(32), e -> brick.moveBetween(WIDTH, HEIGHT)));
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(32), e -> author.moveBetween(WIDTH, HEIGHT)));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    private void initCanvas() {
        itemCanvas = new Canvas(WIDTH - WIDTH / 9, HEIGHT / 1.4);
        itemCanvas.getGraphicsContext2D().setFill(Color.web("4e4c8b"));
        itemCanvas.getGraphicsContext2D().fillRect(0, 0, widthOf(itemCanvas), heightOf(itemCanvas));
        setXY(itemCanvas, WIDTH / 2 - halfWidthOf(itemCanvas), HEIGHT - heightOf(itemCanvas) - 40 * SIZE_RATIO);
        itemCanvas.setEffect(new GaussianBlur(45));
        itemCanvas.setOpacity(.5);
    }

    private void initItems() {
        items = new ImageView[10];
        itemInfos = TextHandler.getItemDescriptions();
        for (int i = 0; i < items.length; i++) {
            items[i] = new ImageView(ImageHandler.getItemImage(i, true));
            items[i].setFitHeight(heightOf(items[i]) * SIZE_RATIO);
            items[i].setFitWidth(widthOf(items[i]) * SIZE_RATIO);
            items[i].setEffect(ITEM_EFFECT);
        }
        for (int i = 0; i < 5; i++) {
            setX(items[i], (xOf(itemCanvas)) + 40 * SIZE_RATIO);
            setY(items[i], (yOf(itemCanvas) + 40 * SIZE_RATIO + i * heightOf(items[i])) + i * 24 * SIZE_RATIO);
            setX(itemInfos[i], xOf(items[i]) + widthOf(items[i]) + 16 * SIZE_RATIO);
            setY(itemInfos[i], yOf(items[i]) + halfHeightOf(items[i]) + 8 * SIZE_RATIO);
        }
        for (int i = 5; i < items.length; i++) {
            setX(items[i], xOf(itemCanvas) + 20 + halfWidthOf(itemCanvas));
            setY(items[i], yOf(items[i - 5]));
            setX(itemInfos[i], xOf(items[i]) + widthOf(items[i]) + 16 * SIZE_RATIO);
            setY(itemInfos[i], yOf(items[i]) + halfHeightOf(items[i]) + 8 * SIZE_RATIO);
        }
    }

    public void runAnimation() {
        animation.play();
    }

    public void stopAnimation() {
        animation.stop();
    }
}
