package io.github.ser215_team11.monopoly.client;

import java.util.Random;

/**
 * A space where the player has to draw a card that has some kind of effect.
 */
public class CardSpace implements BoardSpace {

	private String name;
	private Card []cards;
	private int drawPlace;
	private int []drawOrder;

	/**
	 * Constructs a new card space with the given name and array of cards to
	 * choose randomly from.
	 *
	 * @param name the name of the card space, probably either "Chance" or "Community Chest"
	 * @param cards an array of cards in the deck
	 */
	public CardSpace(String name, Card []cards) {
		this.cards = cards;

		this.drawPlace = 0;
		this.drawOrder = new int[cards.length];
		for(int i=0; i<drawOrder.length; i++) {
			drawOrder[i] = i;
		}

		// Implement the Fisher-Yates shuffle to shuffle the cards.
		Random rand = new Random();
		for(int i=0; i<drawOrder.length; i++) {
			int k = rand.nextInt(drawOrder.length-i);
			int temp = drawOrder[drawOrder.length-i-1];
			drawOrder[drawOrder.length-i-1] = drawOrder[k];
			drawOrder[k] = temp;
		}
	}

	/**
	 * Draws a randomly selected card from the deck that has not been drawn yet.
	 * If the deck is out of cards, it will automatically repeat the last order.
	 *
	 * @return the drawn card
	 */
	public Card draw() {
		Card card = cards[drawOrder[drawPlace]];
		drawPlace++;
		if(drawPlace == drawOrder.length) {
			drawPlace = 0;	// If we're out of cards, repeat the deck
		}
		return card;
	}

	/**
	 * Returns the name of the card space. Probably either "Chance" or
	 * "Community Chest"
	 *
	 * @return space name
	 */
	public String getName() {
		return name;
	}

}
