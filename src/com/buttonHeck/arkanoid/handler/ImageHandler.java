package com.buttonHeck.arkanoid.handler;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class ImageHandler {

    private static BufferedImage ballSW, buttonSW[], bricksSW[], menuItemsSW[], gameItemsSW[], onSW, offSW, paddleSW, magnetPaddleSW;
    private static Image ball, button[], bricks[], menuItems[], gameItems[], on, off, paddle, magnetPaddle;

    static {
        createSwingImages();
        createFXImages();
    }

    private static void createSwingImages() {
        try {
            BufferedImage sheet = ImageIO.read(ImageHandler.class.getResource("/img/spritesheet.png"));
            ballSW = sheet.getSubimage(0, 80, 20, 20);

            buttonSW = new BufferedImage[2];
            buttonSW[0] = getScaledBufferedImage(sheet.getSubimage(20, 80, 40, 20), 5);
            buttonSW[1] = getScaledBufferedImage(sheet.getSubimage(20, 80, 40, 20), 5);
            changeColor(buttonSW[1], 0xFFdcdcdc, 0xFF666666);

            bricksSW = new BufferedImage[10];
            for (int i = 0; i < 5; i++) {
                bricksSW[i] = getScaledBufferedImage(sheet.getSubimage(i * 40, 100, 40, 20), 2);
                bricksSW[i + 5] = getScaledBufferedImage(sheet.getSubimage(i * 40, 120, 40, 20), 2);
            }

            menuItemsSW = new BufferedImage[10];
            for (int i = 0; i < 5; i++) {
                menuItemsSW[i] = getScaledBufferedImage(sheet.getSubimage(i * 40, 0, 40, 40), 2);
                menuItemsSW[i + 5] = getScaledBufferedImage(sheet.getSubimage(i * 40, 40, 40, 40), 2);
            }
            gameItemsSW = new BufferedImage[10];
            for (int i = 0; i < 5; i++) {
                gameItemsSW[i] = sheet.getSubimage(i * 40, 0, 40, 40);
                gameItemsSW[i + 5] = sheet.getSubimage(i * 40, 40, 40, 40);
            }

            onSW = getScaledBufferedImage(sheet.getSubimage(0, 80, 20, 20), 2);
            offSW = getScaledBufferedImage(sheet.getSubimage(0, 80, 20, 20), 2);
            changeColor(onSW, 0xFFdcdcdc, 0xFF38ff30);
            changeColor(onSW, 0xFFb3b3b3, 0xFF2ec02c);
            changeColor(offSW, 0xFFdcdcdc, 0xFFdc0000);
            changeColor(offSW, 0xFFb3b3b3, 0xFFaa0000);
            paddleSW = getScaledBufferedImage(sheet.getSubimage(60, 96, 40, 4), 4);
            magnetPaddleSW = getScaledBufferedImage(sheet.getSubimage(60, 80, 40, 4), 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFXImages() {
        ball = SwingFXUtils.toFXImage(ballSW, null);

        button = new Image[buttonSW.length];
        for (int i = 0; i < buttonSW.length; i++)
            button[i] = SwingFXUtils.toFXImage(buttonSW[i], null);

        bricks = new Image[bricksSW.length];
        for (int i = 0; i < bricks.length; i++)
            bricks[i] = SwingFXUtils.toFXImage(bricksSW[i], null);

        menuItems = new Image[menuItemsSW.length];
        for (int i = 0; i < menuItemsSW.length; i++)
            menuItems[i] = SwingFXUtils.toFXImage(menuItemsSW[i], null);

        gameItems = new Image[gameItemsSW.length];
        for (int i = 0; i < gameItemsSW.length; i++) {
            gameItems[i] = SwingFXUtils.toFXImage(menuItemsSW[i], null);
        }

        on = SwingFXUtils.toFXImage(onSW, null);
        off = SwingFXUtils.toFXImage(offSW, null);
        paddle = SwingFXUtils.toFXImage(paddleSW, null);
        magnetPaddle = SwingFXUtils.toFXImage(magnetPaddleSW, null);
    }

    private static void changeColor(BufferedImage image, int oldColor, int newColor) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) == oldColor) {
                    image.setRGB(x, y, newColor);
                }
            }
        }
    }

    private static BufferedImage getScaledBufferedImage(BufferedImage original, int scale) {
        int newWidth = original.getWidth() * scale;
        int newHeight = original.getHeight() * scale;
        BufferedImage result = new BufferedImage(newWidth, newHeight, original.getType());
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int color = original.getRGB(x, y);
                for (int yOffset = 0; yOffset < scale; yOffset++) {
                    for (int xOffset = 0; xOffset < scale; xOffset++) {
                        result.setRGB(x * scale + xOffset, y * scale + yOffset, color);
                    }
                }
            }
        }
        return result;
    }

    //getters and setters
    public static Image getButtonImage(boolean hovered) {
        return button[hovered ? 0 : 1];
    }

    public static Image getSwitchImage(boolean isOn) {
        return isOn ? on : off;
    }

    public static Image getItemImage(int index, boolean isMenu) {
        return isMenu ? menuItems[index] : gameItems[index];
    }

    public static Image getBrickImage(int index) {
        return bricks[index];
    }

    public static Image getBallImage() {
        return ball;
    }

    public static Image getPaddleImage(boolean isMagnet) {
        return isMagnet ? magnetPaddle : paddle;
    }
}
