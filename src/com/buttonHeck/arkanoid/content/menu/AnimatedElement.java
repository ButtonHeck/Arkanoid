package com.buttonHeck.arkanoid.content.menu;

import com.buttonHeck.arkanoid.HelperMethods;
import javafx.scene.Group;

import java.util.Random;

import static com.buttonHeck.arkanoid.HelperMethods.*;

abstract class AnimatedElement extends Group {

    static final Random RANDOM = new Random(System.currentTimeMillis());

    protected boolean up, left;
    protected double dx, dy, rotation;

    AnimatedElement(double x, double y, double degree) {
        HelperMethods.setX(this, x);
        HelperMethods.setY(this, y);
        up = RANDOM.nextBoolean();
        left = RANDOM.nextBoolean();
        dx = RANDOM.nextDouble() / 2;
        dy = RANDOM.nextDouble() / 2;
        rotation = RANDOM.nextDouble() * 1.4;
        setRotate(degree);
    }

    void moveBetween(double maxWidth, double maxHeight) {
        if (xOf(this) <= 0)
            left = false;
        if (xOf(this) + widthOf(this) >= maxWidth)
            left = true;
        if (yOf(this) <= 0)
            up = false;
        if (yOf(this) + heightOf(this) >= maxHeight)
            up = true;
        setXY(this, xOf(this) + (left ? -dx : dx), yOf(this) + (up ? -dy : dy));
        rotateProperty().setValue(getRotate() + rotation);
    }
}
