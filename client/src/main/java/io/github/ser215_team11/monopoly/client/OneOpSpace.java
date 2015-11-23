package io.github.ser215_team11.monopoly.client;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A board space that does one basic task when a player lands on it. The
 * implementation is instance-specific.
 */
public class OneOpSpace implements BoardSpace {

	private String name;
	private String script;

	private Sprite sprite;

	public OneOpSpace(String name, String scriptLoc) throws IOException {
		this.name = name;
		byte[] encoded = Files.readAllBytes(Paths.get(Resources.path(scriptLoc)));
		script = new String(encoded, StandardCharsets.UTF_8);
	}

	/**
	 * Returns the name of the space.
	 * @return space name
	 */
	public String getName() {
		return name;
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
	 * Executes the task of the one op space on the given player.
	 * @param player the player that landed on the space
	 */
	public void run(Player player) {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(script);
		chunk.call();
	}

}
