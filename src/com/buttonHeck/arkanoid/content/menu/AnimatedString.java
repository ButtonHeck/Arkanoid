package com.buttonHeck.arkanoid.content.menu;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class AnimatedString extends AnimatedElement {

    AnimatedString(String title, double x, double y, double degree) {
        super(x, y, degree);
        Text text = new Text(title);
        text.setFill(Color.WHITE);
        text.setFont(new Font(15));
        text.setEffect(new GaussianBlur(1));
        getChildren().add(text);
    }
}
