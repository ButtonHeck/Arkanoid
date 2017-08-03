package com.buttonHeck.arkanoid.content.game;

import com.buttonHeck.arkanoid.handler.ImageHandler;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static com.buttonHeck.arkanoid.HelperMethods.*;

class LifeCounter {

    private final int DEFAULT;
    private ArrayList<ImageView> lives;
    private int ballsLeft, prevRoundBallLeft;
    private GameRoot holderRoot;

    LifeCounter(int livesCount, GameRoot holderRoot) {
        lives = new ArrayList<>();
        DEFAULT = livesCount;
        ballsLeft = livesCount;
        prevRoundBallLeft = ballsLeft;
        this.holderRoot = holderRoot;
    }

    void reset() {
        lives.clear();
        ballsLeft = prevRoundBallLeft;
        for (int i = 0; i < ballsLeft; i++)
            lives.add(new ImageView(ImageHandler.getSwitchImage(true)));
        for (int i = 0; i < lives.size(); i++)
            setXY(lives.get(i), 20 + i * widthOf(lives.get(i)) * 1.2, 30);
        holderRoot.getChildren().addAll(lives);
    }

    ImageView missedBall() {
        --ballsLeft;
        ImageView missedBall = lives.get(ballsLeft);
        lives.remove(lives.get(ballsLeft));
        return missedBall;
    }

    ImageView addedBall() {
        ImageView life = new ImageView(ImageHandler.getSwitchImage(true));
        lives.add(life);
        setXY(life, 20 + ballsLeft * widthOf(lives.get(ballsLeft)) * 1.2, 30);
        ++ballsLeft;
        return life;
    }

    int getBallsLeft() {
        return ballsLeft;
    }

    void saveNewRoundBallsCount() {
        prevRoundBallLeft = ballsLeft;
    }

    void setBallsLeftToDefault() {
        prevRoundBallLeft = DEFAULT;
    }
}
