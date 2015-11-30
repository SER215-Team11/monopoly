package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * Represents a single die.
 */
public class Die {

    private int x;
    private int y;
    private int num;
    private Sprite[] sprites;

    public Die() throws IOException {
        sprites = new Sprite[6];
        for(int i=0; i<6; i++) {
            sprites[i] = new Sprite("/images/die/" + (i + 1) + ".png");
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        sprites[num].setX(x);
        sprites[num].setY(y);
        sprites[num].draw(g, observer);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return sprites[num].getWidth();
    }

    public int getHeight() {
        return sprites[num].getHeight();
    }

    public void setNum(int num) {
        if(num < 1 || num > 6) {
            throw new RuntimeException("invalid die num " + num);
        }

        this.num = num - 1;
    }

}
