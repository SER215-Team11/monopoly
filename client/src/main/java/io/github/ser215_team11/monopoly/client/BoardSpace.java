package io.github.ser215_team11.monopoly.client;

/**
 * A space on the game board.
 */
public interface BoardSpace {
	/**
	 * Returns a unique space name, like the name of a property or "Go To Jail".
	 *
	 * @return unique space name
	 */
	public String getName();
}
