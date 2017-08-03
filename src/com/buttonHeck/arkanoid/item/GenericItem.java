package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.handler.ImageHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

import java.util.Random;

import static com.buttonHeck.arkanoid.HelperMethods.*;
import static java.lang.Math.random;

public abstract class GenericItem extends Group {

    private static final Scale scale = new Scale(0.66, 0.66);
    private final int ID;
    private static Random random = new Random(System.currentTimeMillis());
    private ImageView image;
    private double dx, dy;
    private boolean left;

    protected GenericItem(int id, double x, double y) {
        ID = id;
        image = new ImageView(ImageHandler.getItemImage(id, false));
        dx = random() * 0.825;
        left = random.nextBoolean();
        dy = (random() * -3.3 - 3.3) / 14;
        getChildren().add(image);
        setXY(this, x, y);
        getTransforms().add(scale);
        setOpacity(0.86);
    }

    public void move() {
        if (xOf(this) <= 0)
            left = false;
        if (xOf(this) + widthOf(this) >= Game.getGameScreenWidth())
            left = true;
        setXY(this, xOf(this) + (left ? -dx : dx), yOf(this) + dy);
        dy += 0.00079;
    }

    public abstract void applyAction();
}
