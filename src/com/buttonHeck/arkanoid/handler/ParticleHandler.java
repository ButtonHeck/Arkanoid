package com.buttonHeck.arkanoid.handler;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.brick.Particle;
import com.buttonHeck.arkanoid.content.game.GameRoot;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Iterator;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public class ParticleHandler {

    private static final double WIDTH = Game.getGameScreenWidth();
    private static final double HEIGHT = Game.getGameScreenHeight();

    private GameRoot holderRoot;
    private ArrayList<Particle> particles;

    public ParticleHandler(GameRoot holderRoot) {
        this.holderRoot = holderRoot;
        particles = new ArrayList<>();
    }

    public void spawnParticles(int direction, int type, Node collidingObject) {
        for (int i = 0; i < 32; i++) {
            Particle p = new Particle(direction, type, collidingObject);
            particles.add(p);
            holderRoot.getChildren().add(p);
        }
    }

    public void moveParticles() {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            if (p.getOpacity() == 0 || xOf(p) < 0 || xOf(p) + widthOf(p) > WIDTH || yOf(p) + heightOf(p) > HEIGHT) {
                it.remove();
                holderRoot.getChildren().remove(p);
                continue;
            }
            p.move();
        }
    }

    public void deleteAllParticles() {
        holderRoot.getChildren().removeAll(particles);
        particles.clear();
    }
}
