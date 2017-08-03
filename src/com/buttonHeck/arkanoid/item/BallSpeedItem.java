package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.Ball;

public class BallSpeedItem extends GenericItem {

    private Ball ball;
    private boolean speedUp;

    BallSpeedItem(int id, double x, double y, Ball ball, boolean speedUp) {
        super(id, x, y);
        this.ball = ball;
        this.speedUp = speedUp;
    }

    @Override
    public void applyAction() {
        ball.changeSpeed(speedUp);
    }
}
