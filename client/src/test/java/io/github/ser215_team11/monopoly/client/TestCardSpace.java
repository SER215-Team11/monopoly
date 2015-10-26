package io.github.ser215_team11.monopoly.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import java.util.Map;
import java.util.HashMap;

/**
 * Tests the card space class.
 */
public class TestCardSpace extends TestCase {

	public TestCardSpace(String testName) {
		super(testName);
	}

	public static Test suite() {
        return new TestSuite(TestCardSpace.class);
    }

	/**
	 * Tests that no repeats appear when cards are drawn.
	 */
	public void testNoRepeats() {
		// Use a hash map to keep track of drawn cards
		Map<Card, Integer> drawnCards = new HashMap<>();

		// Make an array of cards with a unique set of names
		Card []cards = new Card[50];
		for(int i=0; i<cards.length; i++) {
			String name = "";
			for(int j=0; j<i; j++) {
				name += "T";
			}
			cards[i] = new Card(name, "", "");
		}

		CardSpace cardSpace = new CardSpace("Community Chest", cards);
		// Draw all the cards
		for(int i=0; i<cards.length; i++) {
			Card card = cardSpace.draw();
			// Check if the card has already been drawn
			if(drawnCards.containsKey(card)) {
				fail("card space repeated a card before deck was empty");
			}
			drawnCards.put(card, 0);	// Register that the card has been drawn
		}
	}

	/**
	 * Tests that repeats happen when the deck is empty.
	 */
	public void testRepeatsOnEmptyDeck() {
		// Use a hash map to keep track of drawn cards
		Map<Card, Integer> drawnCards = new HashMap<>();

		// Make an array of cards with a unique set of names
		Card []cards = new Card[50];
		for(int i=0; i<cards.length; i++) {
			String name = "";
			for(int j=0; j<i; j++) {
				name += "T";
			}
			cards[i] = new Card(name, "", "");
		}

		CardSpace cardSpace = new CardSpace("Community Chest", cards);
		// Draw all the cards
		for(int i=0; i<cards.length; i++) {
			Card card = cardSpace.draw();
			// Check if the card has already been drawn
			if(drawnCards.containsKey(card)) {
				fail("card space repeated a card before deck was empty");
			}
			drawnCards.put(card, 0);	// Register that the card has been drawn
		}
		// Draw all the cards again
		for(int i=0; i<cards.length; i++) {
			Card card = cardSpace.draw();
			// Check if the card has not already been drawn
			if(!drawnCards.containsKey(card)) {
				fail("a unique card was found even though the deck had been emptied, or a card has appeared twice after the deck had been emptied");
			}
			drawnCards.remove(card);	// Register that the card has been redrawn
		}
	}

}
