package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.Ball;

public class BallThruItem extends GenericItem {

    private Ball ball;

    BallThruItem(int id, double x, double y, Ball ball) {
        super(id, x, y);
        this.ball = ball;
    }

    @Override
    public void applyAction() {
        ball.setBrickThru();
    }
}
