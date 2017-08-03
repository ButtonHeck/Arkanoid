package com.buttonHeck.arkanoid.content.game;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.handler.ImageHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public class Paddle extends Group {

    private final Glow MAGNET_FX = new Glow(0.6);
    private final Lighting PISTOL_FX = new Lighting(new Light.Point(0, 0, 12, Color.SNOW));
    private final Timeline magnetAnimation;
    private ImageView paddleBody;
    private boolean magnetOn, pistolOn, magnetIntensityUp = true;
    private ArrayList<Rectangle> pistols;
    private double widthRatio;

    Paddle() {
        paddleBody = new ImageView(ImageHandler.getPaddleImage(false));
        getChildren().add(paddleBody);
        paddleBody.setFitWidth(widthOf(paddleBody) * Game.getSizeRatio());
        paddleBody.setFitHeight(heightOf(paddleBody) * Game.getSizeRatio());
        setEffect(new InnerShadow(16,Color.BLACK));

        magnetAnimation = new Timeline();
        magnetAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(25), e -> {
            MAGNET_FX.setLevel(MAGNET_FX.getLevel() + (magnetIntensityUp ? 0.075 : -0.075));
            if (MAGNET_FX.getLevel() > 0.95)
                magnetIntensityUp = false;
            if (MAGNET_FX.getLevel() < 0.05)
                magnetIntensityUp = true;
        }));
        magnetAnimation.setCycleCount(Animation.INDEFINITE);
        initPistols();
    }

    private void initPistols() {
        Rectangle left = new Rectangle(8, 12);
        setupPistol(left, 16, -4);
        Rectangle right = new Rectangle(8, 12);
        setupPistol(right, widthInParentOf(this) - 16 - 8, -4);
        pistols = new ArrayList<>();
        pistols.add(left);
        pistols.add(right);
    }

    private void setupPistol(Rectangle pistol, double x, double y) {
        pistol.setFill(Color.GOLD);
        pistol.setEffect(PISTOL_FX);
        pistol.setArcWidth(4);
        pistol.setArcHeight(6);
        setXY(pistol, x, y);
    }

    void reset() {
        getTransforms().clear();
        setMagnet(false);
        magnetAnimation(false);
        setPistol(false);
        widthRatio = 1.0;
        setX(this, Game.getGameScreenWidth() / 2 - halfWidthOf(this));
        setY(this, Game.getGameScreenHeight() - heightOf(this));
    }

    public void setMagnet(boolean isMagnetOn) {
        magnetOn = isMagnetOn;
        paddleBody.setImage(ImageHandler.getPaddleImage(isMagnetOn));
    }

    boolean isMagnet() {
        return magnetOn;
    }

    void magnetAnimation(boolean on) {
        paddleBody.setEffect(on ? MAGNET_FX : null);
        if (on)
            magnetAnimation.play();
        else
            magnetAnimation.stop();
    }


    boolean hasPistol() {
        return pistolOn;
    }

    public void setPistol(boolean isOn) {
        pistolOn = isOn;
        if (isOn) {
            if (!getChildren().contains(pistols.get(0)))
                getChildren().addAll(pistols);
        } else
            getChildren().removeAll(pistols);
    }

    public double getPistolCenter(boolean left) {
        Rectangle pistol = pistols.get(left ? 0 : 1);
        return (pistol.getBoundsInParent().getMaxX() - halfWidthInParentOf(pistol)) * widthRatio;
    }

    public void resize(boolean sizeUp) {
        getTransforms().add(new Scale(sizeUp ? 1.15 : 0.86, 1.0));
        widthRatio *= sizeUp ? 1.15 : 0.86;
    }
}
