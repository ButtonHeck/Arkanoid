package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.Ball;

public class BallShrinkItem extends GenericItem {

    private Ball ball;

    BallShrinkItem(int id, double x, double y, Ball ball) {
        super(id, x, y);
        this.ball = ball;
    }

    @Override
    public void applyAction() {
        ball.shrink();
    }
}
