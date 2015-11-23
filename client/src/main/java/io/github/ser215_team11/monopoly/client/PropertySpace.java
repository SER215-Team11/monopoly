package io.github.ser215_team11.monopoly.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		// TODO: Draw a border to indicate who owns this property, if anyone
	}

	/**
	 * Returns the property this space represents.
	 * @return property
	 */
	public Property getProperty() {
			return property;
		}
		
		private Property[] parseConfig(String path) throws IOException {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			String data = new String(encoded, StandardCharsets.UTF_8);

			JSONObject parent = new JSONObject(data);

			JSONArray jsonProperties = parent.getJSONArray("properties");
			Property[] out = new Property[jsonProperties.length()];
			for(int i=0; i<jsonProperties.length(); i++) {
				JSONObject jsonProperty = jsonProperties.getJSONObject(i);
				out[i] = new Property(jsonProperty.getString("name"),
						jsonProperty.getInt("cost"),
						jsonProperty.getInt("rent"),jsonProperty.getInt("rent1"),
						jsonProperty.getInt("rent2"),jsonProperty.getInt("rent3"),
						jsonProperty.getInt("rent4"),jsonProperty.getInt("rentH"),
						jsonProperty.getInt("mortgage"),jsonProperty.getInt("houseCost"),
						jsonProperty.getInt("hotelCost"));
			}

			return out;
		}

}
