package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * A drawable image.
 */
public class Sprite {

    private int x;
    private int y;

    private Image image;
    private Image origImage;

    public Sprite(Image image) {
        origImage = image;
        this.image = origImage;
    }

    public Sprite(String loc) throws IOException {
        origImage = Resources.getImage(loc);
        image = origImage;
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
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public void scale(double wScale, double hScale) {
        image = ImageUtils.scale(image, wScale, hScale);
    }

    public void rotate(double rot) {
        image = ImageUtils.rotate(image, rot);
    }

    public void reset() {
        image = origImage;
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, x, y, observer);
    }

}
