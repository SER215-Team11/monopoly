package io.github.ser215_team11.monopoly.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;

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
	public void testNoRepeats() throws IOException {
		// Use a hash map to keep track of drawn cards
		Map<Card, Integer> drawnCards = new HashMap<>();

		CardSpace cardSpace = new CardSpace("Community Chest", this.getClass().getResource("/config/test-cards.json").getPath());
		// Check that all the cards were loaded
		Assert.assertEquals("fewer cards were loaded than expected", cardSpace.getCardCnt(), 10);
		// Draw all the cards
		for(int i=0; i<cardSpace.getCardCnt(); i++) {
			Card card = cardSpace.draw();
			// Check if the card has already been drawn
			if(drawnCards.containsKey(card)) {
				fail("card space repeated a card before deck was empty");
			}
			drawnCards.put(card, 0);	// Register that the card has been drawn
		}
		// Check that all the cards were drawn
		Assert.assertEquals(drawnCards.size(), 10);
	}

	/**
	 * Tests that repeats happen when the deck is empty.
	 */
	public void testRepeatsOnEmptyDeck() throws IOException {
		// Use a hash map to keep track of drawn cards
		Map<Card, Integer> drawnCards = new HashMap<>();

		CardSpace cardSpace = new CardSpace("Community Chest", this.getClass().getResource("/config/test-cards.json").getPath());
		// Check that all the cards were loaded
		Assert.assertEquals("fewer cards were loaded than expected", cardSpace.getCardCnt(), 10);
		// Draw all the cards
		for(int i=0; i<cardSpace.getCardCnt(); i++) {
			Card card = cardSpace.draw();
			// Check if the card has already been drawn
			if(drawnCards.containsKey(card)) {
				fail("card space repeated a card before deck was empty");
			}
			drawnCards.put(card, 0);	// Register that the card has been drawn
		}
		// Check that all the cards were drawn
		Assert.assertEquals("fewer cards were drawn than expected", drawnCards.size(), 10);
		// Draw all the cards again
		for(int i=0; i<cardSpace.getCardCnt(); i++) {
			Card card = cardSpace.draw();
			// Check if the card has not already been drawn
			if(!drawnCards.containsKey(card)) {
				fail("a unique card was found even though the deck had been emptied, or a card has appeared twice after the deck had been emptied");
			}
			drawnCards.remove(card);	// Register that the card has been redrawn
		}
		// Check that all the cards were redrawn
		Assert.assertEquals("fewer cards were redrawn than expected", drawnCards.size(), 0);
	}

}
