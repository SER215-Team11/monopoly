package io.github.ser215_team11.monopoly.client;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * A space where the player has to draw a card that has some kind of effect.
 */
public class CardSpace implements BoardSpace {

	private String name;

	private Card[] cards;
	private int drawPlace;
	private int[] drawOrder;

	private Sprite sprite;

	/**
	 * Constructs a new card space with the given name and path to a config file
	 * with card information
	 * @param name the name of the card space, probably either "Chance" or "Community Chest"
	 * @param config path to a config file with card information
	 * @throws IOException indicates lack of resources, should bubble up to the top
	 */
	public CardSpace(String name, String config) throws IOException, FontFormatException {
		this.name = name;
		this.cards = parseConfig(Resources.path(config));

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

	private Card[] parseConfig(String path) throws IOException, FontFormatException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String data = new String(encoded, StandardCharsets.UTF_8);

		JSONObject parent = new JSONObject(data);

		JSONArray jsonCards = parent.getJSONArray("cards");
		Card[] out = new Card[jsonCards.length()];
		for(int i=0; i<jsonCards.length(); i++) {
			JSONObject jsonCard = jsonCards.getJSONObject(i);
			out[i] = new Card(name, jsonCard.getString("description").replace("\\n", "\n"), jsonCard.getString("script"));
		}

		return out;
	}

	/**
	 * Draws a randomly selected card from the deck that has not been drawn yet.
	 * If the deck is out of cards, it will automatically repeat the last order.
	 * @return the drawn card
	 */
	public Card drawCard() {
		Card card = cards[drawOrder[drawPlace]];
		drawPlace++;
		if(drawPlace == drawOrder.length) {
			drawPlace = 0;	// If we're out of cards, repeat the deck
		}
		return card;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	/**
	 * Draws the card space on screen.
	 * @param g the graphics context
	 * @param observer the image observer, which is "this" from the app class
     */
	public void draw(Graphics g, ImageObserver observer) {
		sprite.draw(g, observer);
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

	/**
	 * Returns how many cards are available to the space.
	 * @return number of cards
	 */
	public int getCardCnt() {
		return cards.length;
	}

}
