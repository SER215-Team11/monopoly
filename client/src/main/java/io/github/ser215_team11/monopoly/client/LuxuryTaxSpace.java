package io.github.ser215_team11.monopoly.client;

/**
 * The space on the board that charges the user luxury tax.
 */
public class LuxuryTaxSpace implements OneOpSpace {

	/**
	 * Returns the name of the luxury tax space.
	 *
	 * @return space name
	 */
	public String getName() {
		return "Luxury Tax";
	}

	/**
	 * Charges the given player $100. Old Monopoly rules had the user charged
	 * $75, but as of 2008, that was changed.
	 *
	 * @param player the player to be charged
	 */
	public void run(Player player) {
		// Charge the player $100
	}

}
