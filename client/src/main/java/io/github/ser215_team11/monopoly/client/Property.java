package io.github.ser215_team11.monopoly.client;

/**
 * A generic, own-able, and mortgage-able property that opposing players pay rent
 * on.
 */
public interface Property {

	/**
	 * Returns the name of the property.
	 * @return property name
     */
	String getName();

	/**
	 * Returns the cost to buy the property if it is unowned.
	 * @return cost in dollars
     */
	int getCost();

	/**
	 * Returns true if the property is mortgaged.
	 * @return true if mortgaged
     */
	boolean getMortgaged();

	/**
	 * Controls whether the property is mortgaged or not.
	 * @param mortgaged whether or not the property is mortgaged
     */
	void setMortgaged(boolean mortgaged);

	/**
	 * Returns the mortgage rate of the property.
	 * @return mortgage rate in dollars
     */
	int getMortgageRate();

	/**
	 * Returns the rent the given player should pay.
	 * @param owner player that owns the property
	 * @param tenantDiceRoll the roll the tenant made to get to this space
	 * @return rent cost in dollars
     */
	int getRent(Player owner, int tenantDiceRoll);

}
