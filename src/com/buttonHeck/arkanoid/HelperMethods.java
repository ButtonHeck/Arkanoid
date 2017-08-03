package com.buttonHeck.arkanoid;

import javafx.scene.Node;

public class HelperMethods {

    public static double widthOf(Node node) {
        return node.getLayoutBounds().getWidth();
    }

    public static double widthInParentOf(Node node) {return node.getBoundsInParent().getWidth();}

    public static double heightOf(Node node) {
        return node.getLayoutBounds().getHeight();
    }

    public static double heightInParentOf(Node node) {return node.getBoundsInParent().getHeight();}

    public static double halfWidthOf(Node node) {
        return node.getLayoutBounds().getWidth() / 2;
    }

    public static double halfWidthInParentOf(Node node) {return node.getBoundsInParent().getWidth() / 2;}

    public static double halfHeightOf(Node node) {
        return node.getLayoutBounds().getHeight() / 2;
    }

    public static double halfHeightInParentOf(Node node) {return node.getBoundsInParent().getHeight() / 2;}

    public static double xOf(Node node) {
        return node.getTranslateX();
    }

    public static double yOf(Node node) {
        return node.getTranslateY();
    }

    public static void setX(Node node, double x) {
        node.setTranslateX(x);
    }

    public static void setY(Node node, double y) {
        node.setTranslateY(y);
    }

    public static void setXY(Node node, double x, double y) {
        node.setTranslateX(x);
        node.setTranslateY(y);
    }
}
