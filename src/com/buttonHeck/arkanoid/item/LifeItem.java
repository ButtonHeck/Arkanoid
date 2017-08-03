package com.buttonHeck.arkanoid.item;

import com.buttonHeck.arkanoid.content.game.GameRoot;

public class LifeItem extends GenericItem {

    private boolean lifeUp;
    private GameRoot root;

    LifeItem(int id, double x, double y, boolean lifeUp, GameRoot root) {
        super(id, x, y);
        this.lifeUp = lifeUp;
        this.root = root;
    }

    @Override
    public void applyAction() {
        if (lifeUp) {
            root.addLife();
        } else {
            root.ballMissed();
        }
    }
}
