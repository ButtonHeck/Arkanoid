package com.buttonHeck.arkanoid.brick;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

import static com.buttonHeck.arkanoid.HelperMethods.*;
import static java.lang.Math.random;

public class Particle extends Group {

    public static final int UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
    private static Random random = new Random(System.currentTimeMillis());

    private Shape shape;
    private Color color;
    private double dx, dy, dOp;

    public Particle(int direction, int type, Node collidableObject) {
        chooseShapeType(type);
        chooseShapeColor(type);
        chooseShapeMovementLogic(direction, collidableObject);
        getChildren().add(shape);
    }

    private void chooseShapeType(int type) {
        shape = type == 0 || type == 2 || type == 3 || type == 6 || type == 7
                ? new Rectangle(random.nextInt(2) + 3, random.nextInt(2) + 3)
                : new Circle(random.nextInt(1) + 2);
    }

    private void chooseShapeColor(int type) {
        switch (type) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.PINK;
                break;
            case 2:
                color = Color.BLUEVIOLET.brighter();
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.GREEN;
                break;
            case 5:
                color = Color.LIGHTBLUE;
                break;
            case 6:
                color = Color.WHITE;
                break;
            case 7:
                color = Color.CHOCOLATE;
                break;
            case 8:
                color = Color.LIGHTGRAY;
                break;
            case 9:
                color = Color.LIGHTPINK;
                break;
            default:
                color = Color.WHITE;
        }
        shape.setFill(color);
        dOp = random() * 0.0088;
    }

    private void chooseShapeMovementLogic(int direction, Node collidableObject) {
        if (direction == LEFT) {
            dx = (random() * -3.3 + 0.66) / 10;
            dy = (random() * 6.6 - 4.4) / 40;
            setX(this, collidableObject.getBoundsInParent().getMaxX());
            setY(this, collidableObject.getBoundsInParent().getMaxY() - halfHeightOf(collidableObject));
        } else if (direction == RIGHT) {
            dx = (random() * 3.3 - 0.66) / 10;
            dy = (random() * 6.6 - 4.4) / 40;
            setX(this, collidableObject.getBoundsInParent().getMinX());
            setY(this, collidableObject.getBoundsInParent().getMaxY() - halfHeightOf(collidableObject));
        } else if (direction == UP) {
            dx = (random() * 6.6 - 3.3) / 13;
            dy = (random() * -2.2 + 1.1) / 40;
            setX(this, collidableObject.getBoundsInParent().getMaxX() - halfWidthOf(collidableObject));
            setY(this, collidableObject.getBoundsInParent().getMaxY());
        } else if (direction == DOWN) {
            dx = (random() * 6.6 - 3.3) / 13;
            dy = (random() * -4.4 + 2.2) / 40;
            setX(this, collidableObject.getBoundsInParent().getMaxX() - halfWidthOf(collidableObject));
            setY(this, collidableObject.getBoundsInParent().getMinY());
        }
    }

    public void move() {
        setXY(this, xOf(this) + dx, yOf(this) + dy);
        dy += 0.00144;
        setOpacity(getOpacity() - dOp);
    }
}
