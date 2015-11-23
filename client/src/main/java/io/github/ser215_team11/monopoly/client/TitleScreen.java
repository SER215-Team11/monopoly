package io.github.ser215_team11.monopoly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the title screen.
 */
public class TitleScreen {

    private enum MenuType {
        MAIN, PLAYER_COUNT
    }

    private MenuType currMenu;

    private Sprite logo;

    private Map<MenuType, ArrayList<Button>> menues;

    private int screenWidth;
    private int screenHeight;

    private int playerCnt;
    private boolean finished;

    /**
     * Load resources for the title screen.
     * @param parent The parent JFrame that receives user input
     * @param screenWidth the width of the destination screen
     * @param screenHeight the height of the destination screen
     * @throws IOException, FontFormatException lack of resources. This should bubble up to the top
     */
    public TitleScreen(JFrame parent, int screenWidth, int screenHeight) throws IOException, FontFormatException {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        menues = new HashMap<>();
        currMenu = MenuType.MAIN;

        logo = new Sprite("/images/logo.png");
        logo.setX((screenWidth / 2) - (logo.getWidth() / 2));
        logo.setY((screenHeight / 3) - (logo.getHeight() / 2));

        // Create the main menu
        ArrayList<Button> main = new ArrayList<>();

        Button play = new Button("Play");
        play.setRunnable(() -> switchMenu(MenuType.PLAYER_COUNT) );
        main.add(play);
        Button quit = new Button("Quit");
        quit.setRunnable(() -> System.exit(0) );
        main.add(quit);

        menues.put(MenuType.MAIN, main);

        // Create the player count menu
        ArrayList<Button> playerCount = new ArrayList<>();

        Button twoPlayers = new Button("Two Players");
        twoPlayers.setRunnable(() -> {
            playerCnt = 2;
            finished = true;
        });
        playerCount.add(twoPlayers);
        Button threePlayers = new Button("Three Players");
        threePlayers.setRunnable(() -> {
            playerCnt = 3;
            finished = true;
        });
        playerCount.add(threePlayers);
        Button fourPlayers = new Button("Four Players");
        fourPlayers.setRunnable(() -> {
            playerCnt = 4;
            finished = true;
        });
        playerCount.add(fourPlayers);

        menues.put(MenuType.PLAYER_COUNT, playerCount);

        // Set up the positions of the buttons
        for(ArrayList<Button> menu : menues.values()) {
            Button button = menu.get(0);
            button.setX((screenWidth / 2) - (button.getWidth() / 2));
            button.setY(((screenHeight * 2) / 3) - button.getHeight());
            parent.addMouseListener(button);

            for(int i=1; i<menu.size(); i++) {
                button = menu.get(i);
                parent.addMouseListener(button);
                button.setX((screenWidth / 2) - (button.getWidth() / 2));
                button.setY(menu.get(i-1).getY() + menu.get(i-1).getHeight());
            }
        }

        // Start at the main menu
        switchMenu(MenuType.MAIN);
    }

    /**
     * Switch to the given menu, turning off the buttons from the last menu.
     * @param menuType menu to switch to
     */
    private void switchMenu(MenuType menuType) {
        ArrayList<Button> oldMenu = menues.get(this.currMenu);
        for(Button button : oldMenu) {
            button.setActive(false);
        }
        currMenu = menuType;
        ArrayList<Button> newMenu = menues.get(this.currMenu);
        for(Button button : newMenu) {
            button.setActive(true);
        }
    }

    /**
     * Draws the title screen.
     * @param g graphics context
     * @param observer image observer, usually "this".
     */
    public void draw(Graphics g, ImageObserver observer) {
        logo.draw(g, observer);

        ArrayList<Button> menu = menues.get(currMenu);
        for (Button button : menu) {
            button.draw(g, observer);
        }
    }

    /**
     * Returns true if the title screen has finished and the next screen should
     * start.
     * @return true if title screen is finished
     */
    public boolean getFinished() {
        return finished;
    }

    /**
     * Returns the amount of players the user wants.
     * @return player count
     */
    public int getPlayerCnt() {
        return playerCnt;
    }

}
