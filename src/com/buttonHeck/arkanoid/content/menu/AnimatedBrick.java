package com.buttonHeck.arkanoid.content.menu;

import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class AnimatedBrick extends AnimatedElement {

    private static final InnerShadow FX = new InnerShadow();

    AnimatedBrick(Image brickImage, double x, double y, double degree) {
        super(x, y, degree);
        ImageView brick = new ImageView(brickImage);
        double scaleRatio = RANDOM.nextDouble() * 0.8 + 0.5;
        brick.setScaleX(scaleRatio);
        brick.setScaleY(scaleRatio);
        brick.setEffect(FX);
        getChildren().add(brick);
    }
}
