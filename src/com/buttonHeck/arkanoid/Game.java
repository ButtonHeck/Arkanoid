package com.buttonHeck.arkanoid;

import com.buttonHeck.arkanoid.content.menu.LevelMenuRoot;
import com.buttonHeck.arkanoid.content.menu.MainMenuRoot;
import com.buttonHeck.arkanoid.content.game.GameRoot;
import com.buttonHeck.arkanoid.handler.AudioHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

public class Game extends Application {

    //Application stuff
    private static final int DEFAULT_WIDTH = 1280, DEFAULT_HEIGHT = 800;
    private static double gameScreenWidth = DEFAULT_WIDTH, gameScreenHeight = DEFAULT_HEIGHT, SIZE_RATIO = 1;
    private static double xOffset = 0;
    private static Stage stage;
    private static Scene scene;
    private static Robot mouseLock;

    //Scene Transition Animation
    private static double transitionOpacity = 1.0;
    private static Timeline transitionAnimation;
    private static boolean fadeIn = true;
    private static boolean transitionOn;

    //Scene content
    private static Group currentRoot;
    private static MainMenuRoot menuRoot;
    private static LevelMenuRoot levelRoot;
    private static GameRoot gameRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initScreenParameters();
        initContent();
        initTransitionAnimation();
        createWindow();
        AudioHandler.playMusic();
    }

    private void initScreenParameters() {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        gameScreenHeight = bounds.getHeight();
        gameScreenWidth = gameScreenHeight * 16 / 10;
        double hardwareScreenWidth = bounds.getWidth();
        xOffset = (hardwareScreenWidth - gameScreenWidth) / 2;
        SIZE_RATIO = gameScreenHeight / DEFAULT_HEIGHT;
        try {
            mouseLock = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void initContent() {
        menuRoot = new MainMenuRoot(xOffset);
        levelRoot = new LevelMenuRoot(xOffset);
        gameRoot = new GameRoot(xOffset);
        currentRoot = menuRoot;
    }

    private void initTransitionAnimation() {
        transitionAnimation = new Timeline();
        transitionAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(8), event -> {
            if (transitionOpacity < 1 && !fadeIn) {
                transitionOpacity += 0.02;
                currentRoot.setOpacity(transitionOpacity);
            } else if (transitionOpacity >= 0 && fadeIn) {
                transitionOpacity -= 0.02;
                currentRoot.setOpacity(transitionOpacity);
            }
        }));
        transitionAnimation.setCycleCount(50);
    }

    private void createWindow() {
        scene = new Scene(menuRoot, gameScreenWidth, gameScreenHeight, Color.BLACK);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
        menuRoot.runAnimation();
        scene.setOnKeyPressed(e -> {
            if (currentRoot != gameRoot)
                return;
            if (e.getCode() == KeyCode.ESCAPE && !transitionOn) {
                gameRoot.escape();
                chooseLevel();
            } else if (e.getCode() == KeyCode.P) {
                gameRoot.pause();
            }
        });
        scene.setOnMouseMoved(e -> {
            if (currentRoot == gameRoot) {
                if (e.getSceneX() < xOffset - 2)
                    Platform.runLater(() -> mouseLock.mouseMove((int) xOffset + 1, (int) e.getSceneY()));
                if (e.getSceneX() > gameScreenWidth + xOffset + 2)
                    Platform.runLater(() -> mouseLock.mouseMove((int) (gameScreenWidth + xOffset - 1), (int) e.getSceneY()));
            }
        });
    }

    public static void chooseLevel() {
        if (tryFadeIn()) return;
        scene.setFill(Color.BLACK);
        transitionAnimation.setOnFinished(e -> {
            changeRoot(levelRoot);
            menuRoot.stopAnimation();
            fadeOut();
        });
    }

    public static void backToMenu() {
        if (tryFadeIn()) return;
        transitionAnimation.setOnFinished(e -> {
            changeRoot(menuRoot);
            menuRoot.runAnimation();
            fadeOut();
        });
    }

    public static void startLevel(int level) {
        if (tryFadeIn()) return;
        transitionAnimation.setOnFinished(e -> {
            changeRoot(gameRoot);
            gameRoot.setLevel(level);
            fadeOut();
        });
    }

    private static void changeRoot(Group root) {
        transitionAnimation.stop();
        fadeIn = !fadeIn;
        scene.setCursor(root == gameRoot ? Cursor.NONE : Cursor.DEFAULT);
        scene.setRoot(root);
        currentRoot = root;
        currentRoot.setOpacity(transitionOpacity);
    }

    private static boolean tryFadeIn() {
        if (transitionOn) return true;
        transitionOn = true;
        transitionAnimation.play();
        return false;
    }

    private static void fadeOut() {
        transitionAnimation.play();
        transitionAnimation.setOnFinished(end -> {
            transitionAnimation.stop();
            fadeIn = !fadeIn;
            if (currentRoot == gameRoot)
                scene.setFill(Color.web("2F2F2F"));
            transitionOn = false;
        });
    }

    public static void main(String[] args) {
        launch();
        AudioHandler.finish();
    }

    //Getters

    public static double getGameScreenWidth() {
        return gameScreenWidth;
    }

    public static double getGameScreenHeight() {
        return gameScreenHeight;
    }

    public static double getSizeRatio() {
        return SIZE_RATIO;
    }
}
