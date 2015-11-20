package io.github.ser215_team11.monopoly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * An on-screen clickable button.
 */
public class Button implements MouseListener {

	private int x;
	private int y;

	private BufferedImage sprite;
	private BufferedImage spritePressed;

	private Rectangle bounds;

	private boolean pressed;

	/**
	 * Creates a new button at the given position and text. The text is set to
	 * the default size.
	 * @param x X position of the button
	 * @param y Y position of the button
	 * @param text The text to display on the button
	 * @throws IOException, FontFormatException from lacking resources. These should bubble up to the top
	 */
	public Button(int x, int y, String text) throws IOException, FontFormatException {
		this(x, y, text, 18.0f);
	}

	/**
	 * Creates a new button at the given position and text. The text is set to
	 * the given size.
	 * @param x X position of the button
	 * @param y Y position of the button
	 * @param text The text to display on the button
	 * @param fontSize The font size of the text on the button
	 * @throws IOException, FontFormatException Lacking resources. These should bubble up to the top
	 */
	public Button(int x, int y, String text, float fontSize) throws IOException, FontFormatException {
		// The button is constructed by making a JLabel with a button background
		// and the given text, then drawing to an image that will be used as the
		// button image. The button is not a component, but is created from one.
		this.x = x;
		this.y = y;

		// Load the button backgrounds
		Image bg = Resources.getImage("/images/button.png");
		Image bgPressed = Resources.getImage("/images/button-pressed.png");

		// Create a JLabel that will be used to construct the button images
		JLabel label = new JLabel(new ImageIcon(bg));
		label.setText(text);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setSize(bg.getWidth(null), bg.getHeight(null));
		label.setFont(Resources.getFont("/fonts/kabel.ttf").deriveFont(fontSize));
		label.setForeground(Color.white);

		// Assemble the unpressed button look
		sprite = new BufferedImage(bg.getWidth(null), bg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = sprite.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		label.paint(g);

		// Assemble the pressed button look
		spritePressed = new BufferedImage(bgPressed.getWidth(null), bgPressed.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		label.setIcon(new ImageIcon(bgPressed));
		g = spritePressed.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		label.paint(g);

		bounds = new Rectangle(x+5, y+5, sprite.getWidth(null)-15, sprite.getHeight(null)-15);
	}

	public void draw(Graphics g, ImageObserver observer) {
		if(pressed) {
			g.drawImage(spritePressed, x, y, observer);
		} else {
			g.drawImage(sprite, x, y, observer);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(bounds.contains(e.getPoint())) {
				pressed = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(pressed && bounds.contains(e.getPoint())) {
				pressed = false;
				// Run the press event
			} else if(pressed) {
				pressed = false;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
