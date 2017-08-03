package com.buttonHeck.arkanoid.handler;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.brick.Brick;
import com.buttonHeck.arkanoid.brick.Particle;
import com.buttonHeck.arkanoid.content.game.GameRoot;
import com.buttonHeck.arkanoid.content.game.Paddle;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Iterator;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public class ProjectileHandler {

    private static final double HEIGHT = Game.getGameScreenHeight();
    private static final DropShadow PROJECTILE_FX = new DropShadow(2, 0, 4, Color.GOLD);

    private GameRoot holderRoot;
    private Paddle paddle;
    private ParticleHandler particleHandler;
    private ArrayList<Circle> projectiles;

    public ProjectileHandler(GameRoot holderRoot, Paddle paddle, ParticleHandler particleHandler) {
        this.holderRoot = holderRoot;
        this.paddle = paddle;
        this.particleHandler = particleHandler;
        projectiles = new ArrayList<>();
    }

    public void spawnProjectiles() {
        Circle p1 = new Circle(3);
        setX(p1, xOf(paddle) + paddle.getPistolCenter(true));
        setY(p1, HEIGHT - heightInParentOf(paddle));
        p1.setFill(Color.SNOW);
        p1.setEffect(PROJECTILE_FX);
        Circle p2 = new Circle(3);
        setX(p2, xOf(paddle) + paddle.getPistolCenter(false));
        setY(p2, HEIGHT - heightInParentOf(paddle));
        p2.setFill(Color.SNOW);
        p2.setEffect(PROJECTILE_FX);
        projectiles.add(p1);
        projectiles.add(p2);
        holderRoot.getChildren().addAll(p1, p2);
    }

    public void moveProjectiles() {
        if (!holderRoot.isStarted())
            return;
        Iterator<Circle> it = projectiles.iterator();
        while (it.hasNext()) {
            if (!holderRoot.isStarted())
                return;
            Circle projectile = it.next();
            setY(projectile, yOf(projectile) - 0.92);
            if (yOf(projectile) <= 0) {
                it.remove();
                holderRoot.getChildren().remove(projectile);
                return;
            }
            ArrayList<Brick> bricks = holderRoot.getBricks();
            Iterator<Brick> brickIterator = bricks.iterator();
            while (brickIterator.hasNext()) {
                Brick brick = brickIterator.next();
                if (projectile.getBoundsInParent().intersects(brick.getBoundsInParent())) {
                    particleHandler.spawnParticles(Particle.DOWN, brick.ID, projectile);
                    holderRoot.brickGotHit(brick);
                    brickIterator.remove();
                    it.remove();
                    holderRoot.getChildren().remove(projectile);
                    return;
                }
            }
        }
    }

    public void deleteAllProjectiles() {
        holderRoot.getChildren().removeAll(projectiles);
        projectiles.clear();
    }
}
