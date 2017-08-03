package com.buttonHeck.arkanoid.content.game;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.brick.Brick;
import com.buttonHeck.arkanoid.brick.BrickFactory;
import com.buttonHeck.arkanoid.content.GenericRoot;
import com.buttonHeck.arkanoid.handler.*;
import com.buttonHeck.arkanoid.item.ItemFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public class GameRoot extends GenericRoot {

    //game stuff
    private static final double FRAME_DURATION = 0.0011;
    private static int currentLevel;

    private boolean started = false, paused = false;
    private Timeline gameTimeline, missTimeline;
    private ArrayList<Brick> bricks;
    private Text startHint;
    private Robot mouseLock;
    private double mousePrevX = 0, mouseNewX;
    private GameStateInfoWindow pauseWindow, missWindow;
    private LifeCounter lifeCounter;
    private ItemFactory itemFactory;
    private ItemHandler itemHandler;
    private ParticleHandler particleHandler;
    private ProjectileHandler projectileHandler;

    //movable
    private Paddle paddle;
    private Ball ball;
    private double ballXOffsetToPaddle;

    public GameRoot(double xOffset) {
        super(xOffset);
        initTimelines();
        try {
            mouseLock = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        startHint = TextHandler.getStartHint();
        setXY(startHint, WIDTH / 2 - halfWidthOf(startHint), 2 * heightOf(startHint));

        particleHandler = new ParticleHandler(this);
        ball = new Ball(this, particleHandler);
        paddle = new Paddle();
        projectileHandler = new ProjectileHandler(this, paddle, particleHandler);
        itemFactory = new ItemFactory(this, paddle, ball);
        itemHandler = new ItemHandler(this, itemFactory);
        lifeCounter = new LifeCounter(3, this);
        pauseWindow = new GameStateInfoWindow("PAUSED");
        missWindow = new GameStateInfoWindow("Missed");
    }

    public void setLevel(int level) {
        currentLevel = level;
        mousePrevX = WIDTH / 2;
        getChildren().clear();
        particleHandler.deleteAllParticles();
        itemHandler.deleteAllItems();
        projectileHandler.deleteAllProjectiles();

        getChildren().addAll(background);
        getChildren().add(startHint);
        bricks = BrickFactory.getBricksByID(level);
        getChildren().addAll(bricks);
        ball.reset();
        paddle.reset();
        getChildren().addAll(paddle, ball);
        setXY(ball, xOf(paddle) + halfWidthOf(paddle) - halfWidthOf(ball), yOf(paddle) - heightOf(ball));
        lifeCounter.reset();

        activateMouseEvents();
    }

    private void initTimelines() {
        initGameTimeline();
        initMissTimeline();
    }

    private void initGameTimeline() {
        gameTimeline = new Timeline();
        gameTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(FRAME_DURATION), e -> {
            ball.move();
            ball.checkCollisions(paddle, bricks);
            particleHandler.moveParticles();
            itemHandler.moveItems();
            projectileHandler.moveProjectiles();
            checkLevelCleared();
        }));
        gameTimeline.setCycleCount(Animation.INDEFINITE);
    }

    private void initMissTimeline() {
        missTimeline = new Timeline();
        missTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {/*nop*/}));
        missTimeline.setCycleCount(1);
        missTimeline.setOnFinished(event -> {
            getChildren().remove(missWindow);
            ball.reset();
            paddle.reset();
            setXY(ball, xOf(paddle) + halfWidthOf(paddle) - halfWidthOf(ball), yOf(paddle) - heightOf(ball));
            mousePrevX = WIDTH / 2;
            Platform.runLater(() -> mouseLock.mouseMove((int) WIDTH / 2, (int) HEIGHT / 2));
            activateMouseEvents();
        });
    }

    private void checkLevelCleared() {
        if (bricks.size() == 0) {
            stopGame();
            Game.startLevel(++currentLevel % 10);
            lifeCounter.saveNewRoundBallsCount();
        }
    }

    private void startGame() {
        getChildren().remove(startHint);
        started = true;
        gameTimeline.play();
    }

    public void escape() {
        if (paused) {
            paused = false;
            AudioHandler.pauseMusic(false);
        }
        lifeCounter.setBallsLeftToDefault();
        stopGame();
    }

    private void stopGame() {
        started = false;
        gameTimeline.stop();
        freezeMouseEvents();
        getChildren().removeAll(ball, pauseWindow);
        itemHandler.deleteAllItems();
    }

    public void pause() {
        if (!started)
            return;
        if (!paused) {
            gameTimeline.stop();
            getChildren().add(pauseWindow);
            setOnMouseClicked(null);
            setOnMouseMoved(e -> Platform.runLater(() -> mouseLock.mouseMove((int) mousePrevX, (int) HEIGHT / 2)));
            setOnMouseDragged(e -> Platform.runLater(() -> mouseLock.mouseMove((int) mousePrevX, (int) HEIGHT / 2)));
        } else {
            gameTimeline.play();
            getChildren().remove(pauseWindow);
            activateMouseEvents();
        }
        paused = !paused;
        AudioHandler.pauseMusic(paused);
    }

    public void brickGotHit(Brick brick) {
        AudioHandler.brickHit();
        itemHandler.rollSpawnItemAt(brick);
        getChildren().remove(brick);
    }

    public void ballMissed() {
        AudioHandler.ballMissed();
        freezeMouseEvents();
        started = false;
        gameTimeline.stop();
        missTimeline.play();
        itemHandler.deleteAllItems();
        particleHandler.deleteAllParticles();
        projectileHandler.deleteAllProjectiles();
        getChildren().remove(lifeCounter.missedBall());
        if (lifeCounter.getBallsLeft() == 0) {
            stopGame();
            Game.chooseLevel();
            lifeCounter.setBallsLeftToDefault();
        } else
            getChildren().add(missWindow);
    }

    public void addLife() {
        getChildren().add(lifeCounter.addedBall());
    }

    private void activateMouseEvents() {
        setOnMouseClicked(this::handleMouseClick);
        setOnMouseMoved(this::handleMouseMove);
        setOnMouseDragged(this::handleMouseMove);
    }

    private void handleMouseMove(MouseEvent e) {
        mouseNewX = e.getSceneX();
        ballXOffsetToPaddle = xOf(ball) - xOf(paddle);
        setX(paddle, xOf(paddle) + mouseNewX - mousePrevX);
        if (ball.isMagnetized()
                && xOf(paddle) >= 0
                && xOf(paddle) + widthInParentOf(paddle) <= WIDTH)
            setX(ball, xOf(ball) + mouseNewX - mousePrevX);
        mousePrevX = mouseNewX;
        if (xOf(paddle) + widthInParentOf(paddle) >= WIDTH) {
            setX(paddle, WIDTH - widthInParentOf(paddle));
            if (ball.isMagnetized())
                setX(ball, xOf(paddle) + ballXOffsetToPaddle);
        }
        if (xOf(paddle) <= 0) {
            setX(paddle, 0);
            if (ball.isMagnetized())
                setX(ball, ballXOffsetToPaddle);
        }
        if (!started)
            setX(ball, xOf(paddle) + halfWidthInParentOf(paddle) - halfWidthOf(ball));
    }

    private void handleMouseClick(MouseEvent event) {
        if (!started)
            startGame();
        if (ball.isMagnetized()) {
            ball.demagnetize();
            paddle.magnetAnimation(false);
        }
        if (paddle.hasPistol()) {
            AudioHandler.shot();
            projectileHandler.spawnProjectiles();
        }
    }

    private void freezeMouseEvents() {
        setOnMouseDragged(null);
        setOnMouseClicked(null);
        setOnMouseMoved(null);
    }

    //Getters

    public boolean isStarted() {
        return started;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }
}
