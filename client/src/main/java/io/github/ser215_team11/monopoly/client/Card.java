package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.luaj.vm2.Buffer;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * A single card from the chance or community chest deck.
 */
public class Card {

	private String description;
	private String script;
	private Sprite sprite;

	/**
	 * Constructs a new card with the given description and Lua script.
	 *
	 * @param description a description of what the card does
	 * @param scriptLoc a Lua script that executes the card's task
	 */
	public Card(String deckName, String description, String scriptLoc) throws IOException, FontFormatException {
		this.description = description;
		byte[] encoded = Files.readAllBytes(Paths.get(Resources.path(scriptLoc)));
		script = new String(encoded, StandardCharsets.UTF_8);

		BufferedImage image = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(Resources.getFont("/fonts/impact.ttf").deriveFont(24.0f));
		g.drawImage(Resources.getImage("/images/card.png"), 0, 0, null);
		g.setColor(Color.black);
		g.drawString(deckName, 20, 30);
		g.setFont(Resources.getFont("/fonts/roboto-bold.ttf").deriveFont(14.0f));
		drawString(g, description, 10, 50);
		sprite = new Sprite(image);
	}

	/**
	 * Regular drawString with newline support. Taken from http://stackoverflow.com/a/4413153/2159348.
	 */
	private void drawString(Graphics g, String text, int x, int y) {
		for(String line : text.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}

	/**
	 * Returns a description of what the card does.
	 *
	 * @return card description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the Lua script that is ran when the card is drawn.
	 *
	 * @return card Lua script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * Returns the sprite the card can be drawn with.
	 * @return drawable sprite
     */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Executes the card's task on the given player.
	 *
	 * @param player the player to be operated on
	 */
	public void run(Player player) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(script);
		chunk.call();
	}

}
