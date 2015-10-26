package io.github.ser215_team11.monopoly.client;

import java.util.function.Function;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * A single card from the chance or community chest deck.
 */
public class Card {

	private String name;
	private String description;
	private String script;

	/**
	 * Constructs a new card with the given name, description, and Lua script.
	 *
	 * @param name the name of the card
	 * @param description a description of what the card does
	 * @param script a Lua script that executes the card's task
	 */
	public Card(String name, String description, String script) {
		this.name = name;
		this.description = description;
		this.script = script;
	}

	/**
	 * Returns the name of the card.
	 *
	 * @return card name
	 */
	public String getName() {
		return name;
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
	 * Executes the card's task on the given player.
	 *
	 * @param player the player to be operated on
	 */
	public void run(Player player) {
		PlayerLuaLibrary.setTarget(player);
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(script);
		chunk.call();
	}

}
