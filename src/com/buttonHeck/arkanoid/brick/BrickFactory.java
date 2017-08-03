package com.buttonHeck.arkanoid.brick;

import com.buttonHeck.arkanoid.Game;

import java.util.ArrayList;

import static com.buttonHeck.arkanoid.HelperMethods.setXY;

public abstract class BrickFactory {

    private static final int BRICKS_IN_ROW = 16;
    private static final double SIZE_RATIO = Game.getSizeRatio();
    private static final double SCREEN_WIDTH = Game.getGameScreenWidth();
    private static final double X_OFFSET = SCREEN_WIDTH / 2 - Brick.getBrick(0).getFitWidth() * (BRICKS_IN_ROW / 2);
    private static final ArrayList<Brick> BRICKS = new ArrayList<>();

    public static ArrayList<Brick> getBricksByID(int id) {
        int[][] map = BrickPattern.MAPS[id].MAP;
        BRICKS.clear();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Brick brick = Brick.getBrick(map[y][x]);
                if (brick == null)
                    continue;
                BRICKS.add(brick);
                setXY(brick, X_OFFSET + x * brick.getFitWidth(), 120 * SIZE_RATIO + y * brick.getFitHeight());
            }
        }
        return BRICKS;
    }
}
