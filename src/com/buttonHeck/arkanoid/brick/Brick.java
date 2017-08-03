package com.buttonHeck.arkanoid.brick;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.handler.ImageHandler;
import javafx.scene.CacheHint;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import static com.buttonHeck.arkanoid.HelperMethods.heightOf;
import static com.buttonHeck.arkanoid.HelperMethods.widthOf;

public class Brick extends ImageView {

    public final int ID;
    private final InnerShadow FX = new InnerShadow(5, Color.BLACK);

    private Brick(int ID) {
        this.ID = ID;
        setImage(ImageHandler.getBrickImage(ID));
        setFitWidth(widthOf(this) * Game.getSizeRatio() * 0.95);
        setFitHeight(heightOf(this) * Game.getSizeRatio() * 0.95);
        setEffect(FX);
        setCacheHint(CacheHint.QUALITY);
        setCache(true);
    }

    static Brick getBrick(int id) {
        return id < 0 ? null : new Brick(id);
    }
}
