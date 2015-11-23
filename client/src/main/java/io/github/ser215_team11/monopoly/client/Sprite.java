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

    /**
     * Creates a new sprite with the given image.
     * @param image image that the sprite is operating on
     */
    public Sprite(Image image) {
        origImage = image;
        this.image = origImage;
    }

    /**
     * Creates a new sprite by loading an image from the given location
     * @param loc file location the image will be retrieved from
     * @throws IOException the image does not exist
     */
    public Sprite(String loc) throws IOException {
        origImage = Resources.getImage(loc);
        image = origImage;
    }

    /**
     * Returns the x position the sprite is drawn at.
     * @return x position in pixels
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position the sprite is drawn at.
     * @return y position in pixels
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x position the sprite is drawn at to the given value.
     * @param x position in pixels
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y position the sprite is drawn at to the given value.
     * @param y position in pixels
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the width of the sprite.
     * @return width in pixels
     */
    public int getWidth() {
        return image.getWidth(null);
    }

    /**
     * Returns the height of the sprite.
     * @return height in pixels
     */
    public int getHeight() {
        return image.getHeight(null);
    }

    /**
     * Scales the sprite by the given factors.
     * @param wScale width factor
     * @param hScale height factor
     */
    public void scale(double wScale, double hScale) {
        image = ImageUtils.scale(image, wScale, hScale);
    }

    /**
     * Rotates the sprite by the given amount.
     * @param rot amount in radians
     */
    public void rotate(double rot) {
        image = ImageUtils.rotate(image, rot);
    }

    /**
     * Resets the sprite to its look before any scaling or rotation.
     */
    public void reset() {
        image = origImage;
    }

    /**
     * Draws the sprite on screen.
     * @param g graphics context
     * @param observer image observer
     */
    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, x, y, observer);
    }

}
