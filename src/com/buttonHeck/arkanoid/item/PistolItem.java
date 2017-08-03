package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.Paddle;

public class PistolItem extends GenericItem {

    private Paddle paddle;

    PistolItem(int id, double x, double y, Paddle paddle) {
        super(id, x, y);
        this.paddle = paddle;
    }

    @Override
    public void applyAction() {
        paddle.setPistol(true);
    }
}
