package io.github.ser215_team11.monopoly.client;

/**
 * A board space that does one basic task when a player lands on it. The
 * implementation is instance-specific.
 */
public abstract class OneOpSpace implements BoardSpace {

	/**
	 * Returns the name of the space.
	 *
	 * @return space name
	 */
	public abstract String getName();

	/**
	 * Executes the task of the one op space on the given player.
	 *
	 * @param player the player that landed on the space
	 */
	public abstract void run(Player player);

}
