package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * Manages the main game screen.
 */
public class GameScreen {

    private Board board;

    private int playerCnt;

    public GameScreen() throws IOException {
        board = new Board(150, 150, "/config/board.json");
    }

    public void draw(Graphics g, ImageObserver observer) {
        board.draw(g, observer);
    }

    public void setPlayerCnt(int playerCnt) {
        this.playerCnt = playerCnt;
    }
}
