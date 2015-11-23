package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * A space that contains a property of some type.
 */
public class PropertySpace implements BoardSpace {

	private Property property;

	private Sprite sprite;

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
		// TODO: Get the name from the property when it is implemented
		return "shim";
	}

	public Sprite getSprite() {
		return sprite;
	}

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
	}

	/**
	 * Returns the property this space represents.
	 * @return property
	 */
	public Property getProperty() {
			return property;
		}

}
