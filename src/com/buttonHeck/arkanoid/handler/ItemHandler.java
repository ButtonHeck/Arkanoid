package com.buttonHeck.arkanoid.handler;

import com.buttonHeck.arkanoid.Game;
import com.buttonHeck.arkanoid.Options;
import com.buttonHeck.arkanoid.brick.Brick;
import com.buttonHeck.arkanoid.content.game.GameRoot;
import com.buttonHeck.arkanoid.item.GenericItem;
import com.buttonHeck.arkanoid.item.ItemFactory;
import com.buttonHeck.arkanoid.item.PistolItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.buttonHeck.arkanoid.HelperMethods.*;

public class ItemHandler {

    private static final double HEIGHT = Game.getGameScreenHeight();
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private GameRoot holderRoot;
    private ItemFactory itemFactory;
    private ArrayList<GenericItem> items;

    public ItemHandler(GameRoot holderRoot, ItemFactory itemFactory) {
        this.holderRoot = holderRoot;
        this.itemFactory = itemFactory;
        items = new ArrayList<>();
    }

    public void rollSpawnItemAt(Brick brick) {
        if (!Options.isItemsOn())
            return;
        int num = RANDOM.nextInt(99);
        if (num < 10) {
            AudioHandler.itemSpawn();
            double spawnX = xOf(brick) + halfWidthOf(brick);
            double spawnY = yOf(brick) + halfHeightOf(brick);
            GenericItem item = itemFactory.getItemByID(num, spawnX, spawnY);
            items.add(item);
            holderRoot.getChildren().add(item);
        }
    }

    public void moveItems() {
        Iterator<GenericItem> it = items.iterator();
        while (it.hasNext()) {
            if (!holderRoot.isStarted())
                return;
            GenericItem item = it.next();
            item.move();
            if (item.getBoundsInParent().intersects(holderRoot.getPaddle().getBoundsInParent())) {
                if (item.getClass().equals(PistolItem.class))
                    AudioHandler.pistolPicked();
                else
                    AudioHandler.itemPicked();
                item.applyAction();
                if (holderRoot.isStarted()) {
                    holderRoot.getChildren().remove(item);
                    it.remove();
                }
                continue;
            }
            if (yOf(item) >= HEIGHT) {
                if (holderRoot.isStarted()) {
                    holderRoot.getChildren().remove(item);
                    it.remove();
                }
            }
        }
    }

    public void deleteAllItems() {
        holderRoot.getChildren().removeAll(items);
        items.clear();
    }
}
