package io.github.ser215_team11.monopoly.client;

/**
 * A board space that does one basic task when a player lands on it.
 */
public interface OneOpSpace extends BoardSpace {
	/**
	 * Excecutes the task of the one op space on the given player.
	 *
	 * @param player the player that landed on the space
	 */
	public void run(Player player);
}
