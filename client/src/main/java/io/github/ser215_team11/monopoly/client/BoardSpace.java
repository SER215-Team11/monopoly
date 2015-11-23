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

	void draw(Graphics g, ImageObserver observer);

	void setSprite(Sprite sprite);

	Sprite getSprite();
}
