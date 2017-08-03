package com.buttonHeck.arkanoid.content.game;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.brick.Brick;
import com.buttonHeck.arkanoid.brick.Particle;
import com.buttonHeck.arkanoid.handler.AudioHandler;
import com.buttonHeck.arkanoid.handler.ImageHandler;
import com.buttonHeck.arkanoid.handler.ParticleHandler;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public class Ball extends ImageView {

    private static final double WIDTH = Game.getGameScreenWidth();
    private static final double HEIGHT = Game.getGameScreenHeight();
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final double REFLECT_RATIO = 120.8;
    private static final Scale SHRINK_FX = new Scale(0.66, 0.66);
    private static final Lighting THRU_EFFECT = new Lighting(new Light.Point(4, 4, 16, Color.LIGHTBLUE));

    private double dx, dy, speedUpScale;
    private boolean left, up, shrunk, brickThru, magnetized;
    private GameRoot holderRoot;
    private ParticleHandler particleHandler;

    Ball(GameRoot root, ParticleHandler handler) {
        setImage(ImageHandler.getBallImage());
        holderRoot = root;
        particleHandler = handler;
        setFitWidth(widthOf(this) * Game.getSizeRatio() * 0.8);
        setFitHeight(heightOf(this) * Game.getSizeRatio() * 0.8);
    }

    void checkCollisions(Paddle paddle, ArrayList<Brick> bricks) {
        checkSceneCollision();
        checkBatCollision(paddle);
        checkBrickCollision(bricks);
    }

    private void checkSceneCollision() {
        if (xOf(this) <= 0 && left) {
            left = false;
            AudioHandler.screenReflection(shrunk);
        } else if (xOf(this) + widthInParentOf(this) >= WIDTH && !left) {
            left = true;
            AudioHandler.screenReflection(shrunk);
        } else if (yOf(this) <= 0 && up) {
            up = false;
            AudioHandler.screenReflection(shrunk);
        } else if (yOf(this) + heightInParentOf(this) >= HEIGHT - 4 && !up) {
            holderRoot.ballMissed();
        }
    }

    private void checkBatCollision(Paddle paddle) {
        if (this.getBoundsInParent().getMaxY() >= paddle.getBoundsInParent().getMinY() + (paddle.hasPistol() ? 5 : 0)
                && xOf(this) + halfWidthInParentOf(this) >= xOf(paddle)
                && xOf(this) + halfWidthInParentOf(this) <= xOf(paddle) + widthInParentOf(paddle)) {
            if (!magnetized) {
                up = true;
                left = xOf(this) + halfWidthOf(this) <= xOf(paddle) + halfWidthInParentOf(paddle);
                dx = Math.abs(xOf(paddle) + halfWidthInParentOf(paddle) - xOf(this) - halfWidthInParentOf(this)) / REFLECT_RATIO * speedUpScale;
                if (dx < 0.04)
                    dx = 0.04 * speedUpScale;
            }
            if (paddle.isMagnet()) {
                if (!magnetized)
                    AudioHandler.ballMagnetized();
                magnetized = true;
                paddle.magnetAnimation(true);
            }
            if (!magnetized)
                AudioHandler.batReflection(shrunk);
        }
    }

    private void checkBrickCollision(ArrayList<Brick> bricks) {
        double minX = xOf(this),
                midX = xOf(this) + halfWidthInParentOf(this),
                maxX = xOf(this) + widthInParentOf(this),
                minY = yOf(this),
                midY = yOf(this) + halfHeightInParentOf(this),
                maxY = yOf(this) + heightInParentOf(this);
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if (!this.getBoundsInParent().intersects(brick.getBoundsInParent()))
                continue;
            //brick upper bound
            if (maxY >= brick.getBoundsInParent().getMinY()
                    && ((midX >= brick.getBoundsInParent().getMinX()
                    && midX <= brick.getBoundsInParent().getMaxX()) || (maxY + dy <= brick.getBoundsInParent().getMinY()))
                    && !up) {
                collisionHit(iterator, brick, Particle.UP);
                return;
            }
            //brick lower bound
            if (minY <= brick.getBoundsInParent().getMaxY()
                    && ((midX >= brick.getBoundsInParent().getMinX()
                    && midX <= brick.getBoundsInParent().getMaxX()) || (minY - dy >= brick.getBoundsInParent().getMaxY()))
                    && up) {
                collisionHit(iterator, brick, Particle.DOWN);
                return;
            }
            //brick right bound
            if (minX <= brick.getBoundsInParent().getMaxX()
                    && maxX >= brick.getBoundsInParent().getMaxX()
                    && midY >= brick.getBoundsInParent().getMinY()
                    && midY <= brick.getBoundsInParent().getMaxY()
                    && left) {
                collisionHit(iterator, brick, Particle.RIGHT);
                return;
            }
            //brick left bound
            if (maxX >= brick.getBoundsInParent().getMinX()
                    && minX <= brick.getBoundsInParent().getMinX()
                    && midY >= brick.getBoundsInParent().getMinY()
                    && midY <= brick.getBoundsInParent().getMaxY()
                    && !left) {
                collisionHit(iterator, brick, Particle.LEFT);
                return;
            }
        }
    }

    private void collisionHit(Iterator<Brick> iterator, Brick brick, int direction) {
        particleHandler.spawnParticles(direction, brick.ID, this);
        holderRoot.brickGotHit(brick);
        iterator.remove();
        if (brickThru)
            return;
        if (direction == Particle.UP || direction == Particle.DOWN)
            up = (direction == Particle.UP);
        else
            left = (direction == Particle.LEFT);
    }

    void move() {
        if (magnetized)
            return;
        setXY(this, xOf(this) + (left ? -dx : dx), yOf(this) + (up ? -dy : dy));
    }

    void reset() {
        shrunk = false;
        brickThru = false;
        magnetized = false;
        setEffect(null);
        getTransforms().remove(SHRINK_FX);
        speedUpScale = 1.0;
        up = true;
        left = RANDOM.nextBoolean();
        dx = RANDOM.nextDouble() * 0.6 * speedUpScale;
        dy = 0.56 * speedUpScale;
    }

    public void changeSpeed(boolean speedUp) {
        speedUpScale *= speedUp ? 1.1 : 0.91;
        dy *= speedUpScale;
        dx *= speedUpScale;
    }

    public void shrink() {
        if (shrunk)
            return;
        shrunk = true;
        getTransforms().add(SHRINK_FX);
    }

    public void setBrickThru() {
        if (brickThru)
            return;
        brickThru = true;
        setEffect(THRU_EFFECT);
    }

    boolean isMagnetized() {
        return magnetized;
    }

    void demagnetize() {
        magnetized = false;
    }
}
