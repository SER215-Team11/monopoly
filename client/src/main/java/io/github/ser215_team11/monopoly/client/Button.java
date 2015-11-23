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

	private Sprite sprite;
	private Sprite spritePressed;

	private Runnable runnable;

	private Rectangle bounds;

	private boolean pressed;
	private boolean active;

	/**
	 * Creates a new button at 0, 0 with the given text.
	 * @param text The text to display on the button
	 * @throws IOException, FontFormatException from lacking resources. These should bubble up to the top
     */
	public Button(String text) throws IOException, FontFormatException {
		this(0, 0, text, 18.09f);
	}

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
		BufferedImage spriteImg = new BufferedImage(bg.getWidth(null), bg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = spriteImg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		label.paint(g);
		sprite = new Sprite(spriteImg);
		sprite.setX(x);
		sprite.setY(y);

		// Assemble the pressed button look
		BufferedImage spritePressedImg = new BufferedImage(bgPressed.getWidth(null), bgPressed.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		label.setIcon(new ImageIcon(bgPressed));
		g = spritePressedImg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		label.paint(g);
		spritePressed = new Sprite(spritePressedImg);
		spritePressed.setX(x);
		spritePressed.setY(y);

		bounds = new Rectangle(x+5, y+5, sprite.getWidth()-15, sprite.getHeight()-15);
	}

	/**
	 * Sets whether the button should receive user input or not.
	 * @param active True if the program should receive user input
     */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns the x position of the button.
	 * @return x position in pixels
     */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y position of the button.
	 * @return y position in pixels
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the x position of the button.
	 * @param x position in pixels
     */
	public void setX(int x) {
		this.x = x;
		sprite.setX(x);
		spritePressed.setX(x);
		bounds.setLocation(x, (int) bounds.getY());
	}

	/**
	 * Sets the y position of the button.
	 * @param y position in pixels
	 */
	public void setY(int y) {
		this.y = y;
		sprite.setY(y);
		spritePressed.setY(y);
		bounds.setLocation((int) bounds.getX(), y);
	}

	/**
	 * Returns the width of the button.
	 * @return width in pixels
     */
	public int getWidth() {
		int w1 = sprite.getWidth();
		int w2 = spritePressed.getWidth();

		return (w1 > w2) ? w1 : w2;
	}

	/**
	 * Returns the height of the button.
	 * @return height in pixels
     */
	public int getHeight() {
		int h1 = sprite.getHeight();
		int h2 = spritePressed.getHeight();

		return (h1 > h2) ? h1 : h2;
	}

	/**
	 * Draws the button on screen.
	 * @param g graphics context
	 * @param observer the image observer, which is "this" from the app class
     */
	public void draw(Graphics g, ImageObserver observer) {
		if(pressed) {
			spritePressed.draw(g, observer);
		} else {
			sprite.draw(g, observer);
		}
	}

	/**
	 * Sets the runnable that will be run when the button is clicked.
	 * @param runnable Code to run
     */
	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(bounds.contains(e.getPoint()) && active) {
				pressed = true;
			} else if(!active) {
				pressed = false;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(pressed && bounds.contains(e.getPoint()) && active) {
				pressed = false;
				// Run the on click event if one exists
				if(runnable != null) {
					runnable.run();
				}
			} else if(pressed && active) {
				pressed = false;
			} else if(!active) {
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
