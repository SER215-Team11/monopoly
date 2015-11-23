package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Provides common utilities for manipulating image objects.
 */
public class ImageUtils {

    /**
     * Rotates an Image object. Taken from http://stackoverflow.com/a/4156760/2159348.
     * @param image the image to rotate
     * @param angle rotation in radians
     * @return rotated image
     */
    public static Image rotate(Image image, double angle) {
        BufferedImage bufImg = toBufferedImage(image);
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = bufImg.getWidth(), h = bufImg.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(angle, w/2, h/2);
        g.drawRenderedImage(bufImg, null);
        g.dispose();
        return result;
    }

    /**
     * Converts an image to a buffered image.
     * @param image the image to be converted
     * @return buffered image
     */
    public static BufferedImage toBufferedImage(Image image) {
        if(image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage buff = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buff.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return buff;
    }

    /**
     * Scales an image by the given factor and returns the result.
     * @param image the image to scale
     * @param wScale width scaling factor
     * @param hScale height scaling factor
     * @return scaled image
     */
    public static Image scale(Image image, double wScale, double hScale) {
        BufferedImage dest = new BufferedImage((int) ((double)image.getWidth(null)*wScale),
                (int) ((double)image.getHeight(null)*hScale), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dest.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(wScale, hScale);
        g.drawRenderedImage(toBufferedImage(image), at);
        return dest;
    }

}
