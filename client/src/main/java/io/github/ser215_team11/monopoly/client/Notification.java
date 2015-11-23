package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * Manages the notification that pops up at the top of the screen.
 */
public class Notification {

    private static int screenWidth;
    private static int screenHeight;

    private static int pos;

    private static Image background;
    private static BufferedImage messageImg;
    private static long sinceLastMessage;
    private static Font font;

    public static void init(int screenWidth, int screenHeight) throws IOException, FontFormatException {
        Notification.screenWidth = screenWidth;
        Notification.screenHeight = screenHeight;

        background = Resources.getImage("/images/notification.png");
        font = Resources.getFont("/fonts/kabel.ttf").deriveFont(16.0f);

        pos = -background.getHeight(null);
    }

    public static void notify(String message) {
        BufferedImage placeholder = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = placeholder.createGraphics();
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(message);
        int height = fm.getHeight();
        g.dispose();

        messageImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = messageImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        fm = g.getFontMetrics();
        g.setColor(Color.white);
        g.drawString(message, 0, fm.getAscent());

        pos = -background.getHeight(null);

        sinceLastMessage = System.currentTimeMillis();
    }

    public static void draw(Graphics g, ImageObserver observer) throws IOException {
        if(System.currentTimeMillis() - sinceLastMessage > 4000) {
            // Start moving the notification back up
            if(pos > -background.getHeight(null)) {
                pos -= 10;
            }
        } else {
            if(pos < 0) {
                pos += 10;
            }
        }

        g.drawImage(background, (screenWidth / 2) - (background.getWidth(null) / 2), pos, observer);
        if(messageImg != null) {
            g.drawImage(messageImg, (screenWidth / 2) - (messageImg.getWidth(null) / 2),
                    (background.getHeight(null) / 2) - (messageImg.getHeight(null) / 2) + pos, observer);
        }
    }

}
