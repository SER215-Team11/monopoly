package io.github.ser215_team11.monopoly.client;

/**
 * The space on the board that charges the player income tax.
 */
public class IncomeTaxSpace implements OneOpSpace {

	/**
	 * Returns the name of the income tax space.
	 *
	 * @return space name
	 */
	public String getName() {
		return "Income Tax";
	}

	/**
	 * Charges the given player $200. Old Monopoly rules allowed the user to
	 * be charged 10% instead, but as of 2008, that option has been removed.
	 *
	 * @param player the player to be charged
	 */
	public void run(Player player) {
		// Charge the player $200
	}

}
