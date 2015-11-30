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
    private int moveAmount;
    private long lastMove;
    private int lastRoll;
    private int delayToZoom;

    private int screenWidth;
    private int screenHeight;

    private Button rollButton;
    private Button drawCardButton;
    private Button purchaseButton;
    private Button auctionButton;
    private Button continueButton;
    private Button payRentButton;

    private boolean showDie;
    private Die die1;
    private Die die2;

    private Hud hud;

    private Card currCard;

    public GameScreen(JFrame parent, int screenWidth, int screenHeight) throws IOException, FontFormatException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        board = new Board(140, 140, "/config/board.json");
        BoardLuaLibrary.setBoard(board);
        players = new ArrayList<>();

        // Initialize the heads up display
        hud = new Hud(screenWidth, screenHeight);

        // Create the roll button
        rollButton = new Button("Roll");
        rollButton.setX((screenWidth / 2) - (rollButton.getWidth() / 2));
        rollButton.setY((screenHeight / 2) - (rollButton.getHeight() / 2));
        rollButton.setRunnable(this::roll);
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

        // Create the pay rent button
        payRentButton = new Button("Pay Rent");
        payRentButton.setX((screenWidth / 2) - (continueButton.getWidth() / 2));
        payRentButton.setY(((screenHeight*3) / 4) - (continueButton.getHeight() / 2));
        payRentButton.setActive(false);
        parent.addMouseListener(payRentButton);

        die1 = new Die();
        die1.setX((screenWidth / 2) - die1.getWidth() - 10);
        die1.setY((screenHeight / 2) - (die1.getHeight() / 2));
        die2 = new Die();
        die2.setX(die1.getX() + die2.getWidth() + 10);
        die2.setY(die1.getY());
    }

    /**
     * Initialize the game screen to be ready to display and take input.
     * @param playerCnt the amount of players playing
     * @throws IOException lack of resources. This should bubble up to the top
     */
    public void init(int playerCnt) throws IOException {
        ArrayList<Integer> chosenTokens = new ArrayList<>();
        Random random = new Random();
        // Create all the players
        for(int i=0; i<playerCnt; i++) {
            // Assign a token, making sure that it is unique
            int token;
            boolean taken;
            do {
                taken = false;
                token = random.nextInt(8);
                for(int t : chosenTokens) {
                    if(token == t) {
                        taken = true;
                    }
                }
            } while(taken);
            chosenTokens.add(token);
            Player player = new Player(token);
            players.add(player);
        }

        currPlayer = 0;
        PlayerLuaLibrary.setPlayers(players);
        PlayerLuaLibrary.setCurrPlayer(currPlayer);
        hud.setCurrPlayer(players.get(currPlayer), currPlayer + 1);

        rollButton.setActive(true);

        Notification.notify("Welcome to Monopoly!");
    }

    /**
     * Roll the dice and move the current player that amount.
     */
    private void roll() {
        // Roll the dice
        Random random = new Random();
        int amount1 = (random.nextInt(6) + 1);
        int amount2 = (random.nextInt(6) + 1);
        lastRoll = amount1 + amount2;

        die1.setNum(amount1);
        die2.setNum(amount2);
        showDie = true;
        rollButton.setActive(false);

        // Check if the player is in jail
        if(players.get(currPlayer).getTurnsLeftInJail() == 0) {
            // Set the player ready to move
            moveAmount = amount1 + amount2;
        } else {
            if(amount1 == amount2) {
                // Release the player if they rolled doubles
                players.get(currPlayer).setTurnsLeftInJail(0);
                Notification.notify("Player " + (currPlayer + 1) + " was release from jail!");
                moveAmount = amount1 + amount2;
            } else {
                players.get(currPlayer).decTurnsLeftInJail();
                delayToZoom = 500;
            }
        }
    }

    /**
     * Does the required tasks of the space the current player is on.
     */
    private void processSpace() {
        showDie = false;
        board.zoomPlayer(players.get(currPlayer), screenWidth, screenHeight);

        // Add menu options based on what space was hit
        BoardSpace space = board.getBoardSpace(players.get(currPlayer));
        if(space instanceof CardSpace) {
            // The user is on a card space
            CardSpace cardSpace = (CardSpace) space;
            drawCardButton.setActive(true);

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

            Player owner = getOwner(propertySpace.getProperty());
            if(owner == null) {
                purchaseButton.setActive(true);
                auctionButton.setActive(true);

                // Give the user the property to own
                purchaseButton.setRunnable(() -> {
                    // Check if the player has enough money to buy the property
                    if(players.get(currPlayer).get_money() < propertySpace.getProperty().getCost()) {
                        Notification.notify("You don't have enough money to buy that!");
                    } else {
                        players.get(currPlayer).takeMoney(propertySpace.getProperty().getCost());
                        players.get(currPlayer).addProperty(propertySpace.getProperty());
                        Notification.notify("Player " + (currPlayer + 1) + " bought " + propertySpace.getName() + "!");
                        resetButtons();
                        rollButton.setActive(true);
                        board.zoomOut();
                        turnOver();
                    }
                });

                // Auction the property off to other players
                auctionButton.setRunnable(() -> {
                    resetButtons();
                    rollButton.setActive(true);
                    board.zoomOut();
                    turnOver();
                });
            } else {
                payRentButton.setActive(true);

                payRentButton.setRunnable(() -> {
                    int rent = propertySpace.getProperty().getRent(owner, lastRoll);
                    players.get(currPlayer).takeMoney(rent);
                    owner.giveMoney(rent);

                    resetButtons();
                    rollButton.setActive(true);
                    board.zoomOut();
                    turnOver();
                });
            }
        } else if(space instanceof OneOpSpace) {
            // The user is on a one-op space. This also includes spaces that do nothing, like free parking.
            OneOpSpace oneOpSpace = (OneOpSpace) space;
            continueButton.setActive(true);

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

    private Player getOwner(Property property) {
        for(Player player : players) {
            for(Property p : player.getProperties()) {
                if(p == property) {
                    return player;
                }
            }
        }

        return null;
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
        payRentButton.setActive(false);
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
        hud.setCurrPlayer(players.get(currPlayer), currPlayer + 1);
    }

    /**
     * Draws the board and other game elements.
     * @param g graphics context
     * @param observer image observer, usually "this".
     */
    public void draw(Graphics g, ImageObserver observer) {
        board.draw(g, observer, players, currPlayer);
        board.drawPlayers(players, g, observer);
        // Move the current player every half a second
        if(moveAmount > 0 && System.currentTimeMillis() - lastMove > 500) {
            players.get(currPlayer).setPlayerPos(board.fixBoardPos(players.get(currPlayer).getPlayerPos() + 1));
            moveAmount--;
            if(moveAmount == 0) {
                delayToZoom = 60;
            }
            lastMove = System.currentTimeMillis();
        }

        if(delayToZoom > 0) {
            delayToZoom--;
            if(delayToZoom == 0) {
                // Process the player's current space
                processSpace();
            }
        }

        rollButton.draw(g, observer);
        drawCardButton.draw(g, observer);
        purchaseButton.draw(g, observer);
        auctionButton.draw(g, observer);
        continueButton.draw(g, observer);
        payRentButton.draw(g, observer);

        if(showDie) {
            die1.draw(g, observer);
            die2.draw(g, observer);
        }

        hud.draw(g, observer);

        if(currCard != null) {
            currCard.getSprite().draw(g, observer);
        }
    }
}
