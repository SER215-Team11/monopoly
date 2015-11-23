package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * The heads-up display.
 */
public class Hud {

    private Sprite leftTab;
    private Player player;
    private Font font;

    public Hud() throws IOException, FontFormatException {
        leftTab = new Sprite(Resources.getImage("/images/hud-left.png"));
        font = Resources.getFont("/fonts/kabel.ttf").deriveFont(18.0f);
    }

    public void setCurrPlayer(Player player) {
        this.player = player;
    }

    public void draw(Graphics g, ImageObserver observer) {
        leftTab.draw(g, observer);

        String money = "$" + player.get_money();

        BufferedImage placeholder = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(money);
        int height = fm.getHeight();
        g2d.dispose();

        g.setFont(font);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(money, (leftTab.getWidth() / 2) - (width / 2), (leftTab.getHeight() / 2) + (height / 2));
    }

}
