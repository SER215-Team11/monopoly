package io.github.ser215_team11.monopoly.client;

/**
 * The space on the board that sends the unfortunate player to jail.
 */
public class GoToJailSpace implements OneOpSpace {

	/**
	 * Returns the name of the go to jail space.
	 *
	 * @return space name
	 */
	public String getName() {
		return "Go To Jail";
	}

	/**
	 * Sends the given player to jail.
	 *
	 * @param player to be sent to jail
	 */
	public void run(Player player) {
		// Send the player to jail
	}

}
