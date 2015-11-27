package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * A space that contains a property of some type.
 */
public class PropertySpace implements BoardSpace {

	public enum Ownership {
		OWNED_BY_CURRENT_PLAYER, OWNED_BY_ANOTHER_PLAYER, UNOWNED
	}

	private Property property;

	private Sprite sprite;

	private Ownership ownership;

	/**
	 * Constructs a new property space that refers to the given property.
	 * @param property the property that this space represents
	 */
	public PropertySpace(Property property) {
		this.property = property;
	}

	/**
	 * Returns the name of the property this space represents.
	 * @return property name
	 */
	public String getName() {
		return property.getName();
	}

	/**
	 * Return the current sprite being drawn.
	 * @return current sprite
     */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Sets the sprite to be drawn to the given sprite.
	 * @param sprite sprite to draw
     */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	/**
	 * Draws the card space on screen.
	 * @param g the graphics context
	 * @param observer the image observer, which is "this" from the app class
	 */
	public void draw(Graphics g, ImageObserver observer) {
		sprite.draw(g, observer);
		switch(ownership) {
			case OWNED_BY_CURRENT_PLAYER:
				g.setColor(Color.green);
				break;
			case OWNED_BY_ANOTHER_PLAYER:
				g.setColor(Color.red);
				break;
			case UNOWNED:
				g.setColor(new Color(0, 0, 0, 0));
		}
		((Graphics2D) g).setStroke(new BasicStroke(2.0f));
		g.drawRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}

	/**
	 * Sets the ownership status of the property space to the given value.
	 * @param ownership ownership status from the point of view of the current player
     */
	public void setOwnership(Ownership ownership) {
		this.ownership = ownership;
	}

	/**
	 * Returns the property this space represents.
	 * @return property
	 */
	public Property getProperty() {
		return property;
	}

}
