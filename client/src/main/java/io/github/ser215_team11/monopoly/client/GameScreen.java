package io.github.ser215_team11.monopoly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the main game screen.
 */
public class GameScreen {

    private Board board;

    private ArrayList<Player> players;
    private int currPlayer;

    private int screenWidth;
    private int screenHeight;

    private Button rollButton;
    private Button drawCardButton;
    private Button purchaseButton;
    private Button auctionButton;
    private Button continueButton;

    private Hud hud;

    private Card currCard;

    public GameScreen(JFrame parent, int screenWidth, int screenHeight) throws IOException, FontFormatException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        board = new Board(140, 140, "/config/board.json");
        BoardLuaLibrary.setBoard(board);
        players = new ArrayList<>();

        // Initialize the heads up display
        hud = new Hud();

        // Create the roll button
        rollButton = new Button("Roll");
        rollButton.setX((screenWidth / 2) - (rollButton.getWidth() / 2));
        rollButton.setY((screenHeight / 2) - (rollButton.getHeight() / 2));
        rollButton.setRunnable(this::rollAndMove);
        parent.addMouseListener(rollButton);

        // Create the draw card button
        drawCardButton = new Button("Draw");
        drawCardButton.setX((screenWidth / 2) - (drawCardButton.getWidth() / 2));
        drawCardButton.setY(((screenHeight*3) / 4) - (drawCardButton.getHeight() / 2));
        drawCardButton.setActive(false);
        parent.addMouseListener(drawCardButton);

        // Create the purchase button
        purchaseButton = new Button("Purchase");
        purchaseButton.setX((screenWidth / 3) - (purchaseButton.getWidth() / 2));
        purchaseButton.setY(((screenHeight*3) / 4) - (purchaseButton.getHeight() / 2));
        purchaseButton.setActive(false);
        parent.addMouseListener(purchaseButton);

        // Create the auction button
        auctionButton = new Button("Auction");
        auctionButton.setX(((screenWidth * 2) / 3) - (auctionButton.getWidth() / 2));
        auctionButton.setY(((screenHeight*3) / 4) - (auctionButton.getHeight() / 2));
        auctionButton.setActive(false);
        parent.addMouseListener(auctionButton);

        // Create the continue button
        continueButton = new Button("Continue");
        continueButton.setX((screenWidth / 2) - (continueButton.getWidth() / 2));
        continueButton.setY(((screenHeight*3) / 4) - (continueButton.getHeight() / 2));
        continueButton.setActive(false);
        parent.addMouseListener(continueButton);
    }

    /**
     * Initialize the game screen to be ready to display and take input.
     * @param playerCnt the amount of players playing
     * @throws IOException lack of resources. This should bubble up to the top
     */
    public void init(int playerCnt) throws IOException {
        Random random = new Random();
        for(int i=0; i<playerCnt; i++) {
            Player player = new Player(random.nextInt(8));
            players.add(player);
        }
        currPlayer = 0;
        PlayerLuaLibrary.setPlayers(players);
        PlayerLuaLibrary.setCurrPlayer(currPlayer);
        hud.setCurrPlayer(players.get(currPlayer));

        rollButton.setActive(true);

        Notification.notify("Welcome to Monopoly!");
    }

    /**
     * Rolls the dice and moves the current player by the amount given.
     */
    private void rollAndMove() {
        // Roll the dice
        Random random = new Random();
        int amount = (random.nextInt(6) + 1) + (random.nextInt(6) + 1);

        // Move the player and wrap them around the board if need be
        players.get(currPlayer).addPlayerPos(amount);
        players.get(currPlayer).setPlayerPos(board.fixBoardPos(players.get(currPlayer).getPlayerPos()));
        board.zoomPlayer(players.get(currPlayer), screenWidth, screenHeight);

        // Add menu options based on what space was hit
        BoardSpace space = board.getBoardSpace(players.get(currPlayer));
        Notification.notify("Player " + (currPlayer + 1) + " landed on " + space.getName() + "!");
        if(space instanceof CardSpace) {
            // The user is on a card space
            CardSpace cardSpace = (CardSpace) space;
            drawCardButton.setActive(true);
            rollButton.setActive(false);

            // Show the user the card and apply the effects
            drawCardButton.setRunnable(() -> {
                currCard = cardSpace.drawCard();
                currCard.getSprite().setX((screenWidth / 2) - (currCard.getSprite().getWidth() / 2));
                currCard.getSprite().setY((screenHeight / 2) - (currCard.getSprite().getHeight() / 2));
                currCard.run(players.get(currPlayer));
                resetButtons();
                continueButton.setActive(true);
                continueButton.setRunnable(() -> {
                    currCard = null;
                    resetButtons();
                    rollButton.setActive(true);
                    board.zoomOut();
                    turnOver();
                });
            });
        } else if(space instanceof PropertySpace) {
            // The user is on a property space
            PropertySpace propertySpace = (PropertySpace) space;
            purchaseButton.setActive(true);
            auctionButton.setActive(true);
            rollButton.setActive(false);

            // Give the user the property to own
            purchaseButton.setRunnable(() -> {
                // TODO: Charge the player for the property when properties are implemented
                players.get(currPlayer).addProperty(propertySpace.getProperty());
                Notification.notify("Player " + (currPlayer + 1) + " bought " + propertySpace.getName() + "!");
                resetButtons();
                rollButton.setActive(true);
                board.zoomOut();
                turnOver();
            });

            // Auction the property off to other players
            auctionButton.setRunnable(() -> {
                resetButtons();
                rollButton.setActive(true);
                board.zoomOut();
                turnOver();
            });
        } else if(space instanceof OneOpSpace) {
            // The user is on a one-op space. This also includes spaces that do nothing, like free parking.
            OneOpSpace oneOpSpace = (OneOpSpace) space;
            continueButton.setActive(true);
            rollButton.setActive(false);

            // Run whatever event is appropriate, if any
            continueButton.setRunnable(() -> {
                oneOpSpace.run(players.get(currPlayer));
                resetButtons();
                rollButton.setActive(true);
                board.zoomOut();
                turnOver();
            });
        }
    }

    /**
     * Resets all buttons to be hidden.
     */
    private void resetButtons() {
        rollButton.setActive(false);
        drawCardButton.setActive(false);
        purchaseButton.setActive(false);
        auctionButton.setActive(false);
        continueButton.setActive(false);
    }

    /**
     * Pass the turn off to the next player.
     */
    private void turnOver() {
        currPlayer++;
        if(currPlayer >= players.size()) {
            currPlayer = 0;
        }
        PlayerLuaLibrary.setCurrPlayer(currPlayer);
        hud.setCurrPlayer(players.get(currPlayer));
    }

    /**
     * Draws the board and other game elements.
     * @param g graphics context
     * @param observer image observer, usually "this".
     */
    public void draw(Graphics g, ImageObserver observer) {
        board.draw(g, observer);
        board.drawPlayers(players, g, observer);
        rollButton.draw(g, observer);
        drawCardButton.draw(g, observer);
        purchaseButton.draw(g, observer);
        auctionButton.draw(g, observer);
        continueButton.draw(g, observer);
        hud.draw(g, observer);
        if(currCard != null) {
            currCard.getSprite().draw(g, observer);
        }
    }
}
