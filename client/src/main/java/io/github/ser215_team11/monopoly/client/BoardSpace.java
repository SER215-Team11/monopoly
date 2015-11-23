package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * A space on the game board.
 */
public interface BoardSpace {
	/**
	 * Returns a unique space name, like the name of a property or "Go To Jail".
	 * @return unique space name
	 */
	String getName();

	/**
	 * Draws the board space on screen.
	 * @param g graphics context
	 * @param observer image observer
     */
	void draw(Graphics g, ImageObserver observer);

	/**
	 * Sets the sprite to be drawn to the given sprite.
	 * @param sprite sprite to draw
     */
	void setSprite(Sprite sprite);

	/**
	 * Returns the current sprite that is being drawn.
	 * @return current sprite
     */
	Sprite getSprite();
}
