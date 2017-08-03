package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.Paddle;

public class PaddleSizeItem extends GenericItem {

    private Paddle paddle;
    private boolean sizeUp;

    PaddleSizeItem(int id, double x, double y, Paddle paddle, boolean sizeUp) {
        super(id, x, y);
        this.paddle = paddle;
        this.sizeUp = sizeUp;
    }

    @Override
    public void applyAction() {
        paddle.resize(sizeUp);
    }
}
