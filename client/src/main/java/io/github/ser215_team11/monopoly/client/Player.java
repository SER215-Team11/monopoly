package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a single player on the board.
 */
public class Player {
	private String name;
	private int playerPos;
	private int money;
	private int getOutOfJail;
	private int turnsLeftInJail;
	private boolean stillInGame;
	private ArrayList<Property> properties;
	private Sprite token;

	public Player() {
		playerPos =0;
		money = 1000;
		getOutOfJail = 0;
		turnsLeftInJail =0;
		stillInGame = true;

		properties = new ArrayList<>();
	}

	public Player(int tokenType) throws IOException {
		this();
		token = new Sprite("/images/tokens/" + tokenType + ".png");
	}

	public Sprite getSprite() {
		return token;
	}

	// setter and getter for name
	public void set_name(String playerName)
		{
			name = playerName;
		}
	public String get_name()
		{
			return name;
		}

	// setter and getter for playerPos
	public void setPlayerPos(int playerPos)
		{
			this.playerPos = playerPos;
		}
	public int getPlayerPos()
		{
			return playerPos;
		}

	/**
	 * Adds the given amount to the player position.
	 * @param amount amount to move the player
     */
	public void addPlayerPos(int amount) {
		this.playerPos += amount;
	}

	/**
	 * Sets the player's money to the given value.
	 *
	 * @param amount money in dollars
	 */
	public void set_money(int amount) {
		money = amount;
	}

	/**
	 * Adds the given amount to the player's total money.
	 *
	 * @param amount money in dollars
	 */
	public void giveMoney(int amount) {
		money += amount;
	}

	/**
	 * Subtracts the given amount to the player's total money.
	 *
	 * @param amount money in dollars
	 */
	public void takeMoney(int amount) {
		money -= amount;
	}

	/**
	 * Returns the user's current money in dollars.
	 *
	 * @return player's current money in dollars
	 */
	public int get_money() {
		return money;
	}

	// setter and getter for getOutOfJail
	public void setGetOutOfJailFreeCardCnt(int get_out)
		{
			getOutOfJail = get_out;
		}

	/**
	 * Gives the player one Get Out of Jail Free card.
	 */
	public void giveGetOutOfJailFreeCard() {
		getOutOfJail++;
	}

	public int getGetOutOfJailFreeCards()
		{
			return getOutOfJail;
		}

	// setter and getter for turns left in jail
	public void setTurnsLeftInJail(int turnsLeftInJail)
		{
			this.turnsLeftInJail = turnsLeftInJail;
		}

	public int getTurnsLeftInJail()
		{
			return turnsLeftInJail;
		}

	// setter and getter for still in game
	public void setStillInGame(boolean inJail )
		{
			stillInGame = inJail;
		}

	public boolean getStillInGame()
		{
			return stillInGame;
		}

	/**
	 * Gives the user a property.
	 * @param property property to give the user
     */
	public void addProperty(Property property) {
		properties.add(property);
	}

	/**
	 * Takes away a property from the user.
	 * @param property property to take from the user
     */
	public void removeProperty(Property property) {
		properties.remove(property);
	}

	/**
	 * Returns the list of owned properties
	 * @return list of owned properties
     */
	public ArrayList<Property> getProperties() {
		return properties;
	}
}
