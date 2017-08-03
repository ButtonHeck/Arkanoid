package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.Ball;
import com.buttonHeck.arkanoid.content.game.GameRoot;
import com.buttonHeck.arkanoid.content.game.Paddle;

public class ItemFactory {

    private GameRoot root;
    private Paddle paddle;
    private Ball ball;

    public ItemFactory(GameRoot root, Paddle paddle, Ball ball) {
        this.root = root;
        this.paddle = paddle;
        this.ball = ball;
    }

    public GenericItem getItemByID(int id, double spawnX, double spawnY) {
        switch (id) {
            case 0:
                return new PaddleSizeItem(0, spawnX, spawnY, paddle, true);
            case 1:
                return new PaddleSizeItem(1, spawnX, spawnY, paddle, false);
            case 2:
                return new BallThruItem(2, spawnX, spawnY, ball);
            case 3:
                return new LifeItem(3, spawnX, spawnY, true, root);
            case 4:
                return new LifeItem(4, spawnX, spawnY, false, root);
            case 5:
                return new BallShrinkItem(5, spawnX, spawnY, ball);
            case 6:
                return new MagnetItem(6, spawnX, spawnY, paddle);
            case 7:
                return new BallSpeedItem(7, spawnX, spawnY, ball, true);
            case 8:
                return new BallSpeedItem(8, spawnX, spawnY, ball, false);
            case 9:
                return new PistolItem(9, spawnX, spawnY, paddle);
            default:
                return null;
        }
    }
}
