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
    private Player[] players;

    /**
     * Loads resources for the game screen.
     * @throws IOException Means a lack of resources. Should bubble up to the top.
     */
    public GameScreen() throws IOException {
        board = new Board(150, 150, "/config/board.json");
    }

    /**
     * Draws the board and other game elements.
     * @param g graphics context
     * @param observer image observer, usually "this".
     */
    public void draw(Graphics g, ImageObserver observer) {
        board.draw(g, observer);
    }

    public void setPlayerCnt(int playerCnt) {
        this.playerCnt = playerCnt;
    }
}
