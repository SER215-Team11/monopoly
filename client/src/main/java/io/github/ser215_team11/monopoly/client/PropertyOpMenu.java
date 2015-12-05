package io.github.ser215_team11.monopoly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * Manages the menu for property operations, like houses and mortgaging.
 */
public class PropertyOpMenu {

    private static Font font;
    private static Sprite text;

    private static Button buyUpgradeButton;
    private static Button sellUpgradeButton;
    private static Button mortgageButton;
    private static Button unMortgageButton;

    private static Property property;
    private static Player player;

    private static boolean active;

    private static int screenWidth;
    private static int screenHeight;

    public static void init(int screenWidth, int screenHeight, JFrame parent) throws IOException, FontFormatException {
        PropertyOpMenu.screenWidth = screenWidth;
        PropertyOpMenu.screenHeight = screenHeight;

        buyUpgradeButton = new Button("Buy Upgrade");
        buyUpgradeButton.setX((screenWidth / 3) - (buyUpgradeButton.getWidth() / 2));
        buyUpgradeButton.setY((screenHeight / 3) - (buyUpgradeButton.getHeight() / 2));
        parent.addMouseListener(buyUpgradeButton);

        sellUpgradeButton = new Button("Sell Upgrade");
        sellUpgradeButton.setX(((screenWidth*2) / 3) - (buyUpgradeButton.getWidth() / 2));
        sellUpgradeButton.setY((screenHeight / 3) - (buyUpgradeButton.getHeight() / 2));
        parent.addMouseListener(sellUpgradeButton);

        mortgageButton = new Button("Mortgage");
        mortgageButton.setX(((screenWidth * 2) / 3) - (mortgageButton.getWidth() / 2));
        mortgageButton.setY(((screenHeight * 2) / 3) - (mortgageButton.getHeight() / 2));
        parent.addMouseListener(mortgageButton);

        unMortgageButton = new Button("Unmortgage");
        unMortgageButton.setX(mortgageButton.getX());
        unMortgageButton.setY(mortgageButton.getY());
        parent.addMouseListener(unMortgageButton);

        font = Resources.getFont("/fonts/kabel.ttf");
    }

    public static void setPlayer(Player player) {
        PropertyOpMenu.player = player;
    }

    public static void setProperty(Property property) {
        if(!active) {
            // Don't set the property if the menu should not be active
            return;
        }

        boolean playerOwned = false;
        for(Property p : player.getProperties()) {
            if(p == property) {
                playerOwned = true;
                break;
            }
        }

        if(!playerOwned) {
            return;
        }

        PropertyOpMenu.property = property;

        // Get font information
        BufferedImage placeholder = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setFont(font.deriveFont(32.0f));
        FontMetrics fm = g2d.getFontMetrics();

        // Draw the property name onto an image
        BufferedImage image = new BufferedImage(fm.stringWidth(property.getName()), fm.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setFont(font.deriveFont(32.0f));
        g.setColor(Color.black);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(property.getName(), 0, fm.getHeight());

        // Create and place the property name
        text = new Sprite(image);
        text.setX((screenWidth / 2) - (text.getWidth() / 2));
        text.setY((screenHeight / 3) - (text.getHeight() * 3));

        buyUpgradeButton.setRunnable(() -> {
            if(!(property instanceof StandardProperty)) {
                return;
            }

            StandardProperty standard = (StandardProperty) property;

            if(standard.getNumUpgrades() < 4) {
                if(player.get_money() < standard.getHouseCost()) {
                    Notification.notify("You cannot afford this upgrade!");
                } else {
                    player.takeMoney(standard.getHouseCost());
                    standard.upgrade();
                }
            } else if(standard.getNumUpgrades() == 4) {
                if(player.get_money() < standard.getHotelCost()) {
                    Notification.notify("You cannot afford this upgrade!");
                } else {
                    player.takeMoney(standard.getHotelCost());
                    standard.upgrade();
                }
            }
        });

        sellUpgradeButton.setRunnable(() -> {
            if(!(property instanceof StandardProperty)) {
                return;
            }

            StandardProperty standard = (StandardProperty) property;

            if(standard.getNumUpgrades() == 5) {
                player.giveMoney(standard.getHotelCost() / 2);
                standard.downgrade();
            } else if(standard.getNumUpgrades() >= 1) {
                player.giveMoney(standard.getHouseCost() / 2);
                standard.downgrade();
            }
        });

        mortgageButton.setRunnable(() -> {
            if(property.getMortgaged()) {
                return;
            }

            property.setMortgaged(true);
            player.giveMoney(property.getMortgageRate());
        });

        unMortgageButton.setRunnable(() -> {
            if(!property.getMortgaged()) {
                return;
            }

            if(player.get_money() < property.getMortgageRate()) {
                Notification.notify("You don't have enough money!");
            } else {
                property.setMortgaged(false);
                player.takeMoney(property.getMortgageRate());
            }
        });
    }

    public static void setActive(boolean active) {
        PropertyOpMenu.active = active;
        if(!active) {
            property = null;
        }
    }

    public static void draw(Graphics g, ImageObserver observer) {
        if(property != null && text != null) {
            text.draw(g, observer);
            // If the property is a standard property, the user can upgrade it
            if(property instanceof StandardProperty) {
                StandardProperty standard = (StandardProperty) property;
                boolean monopoly = PropertyLoader.hasMonopoly(player, standard.getColor());

                // Check if there are upgrades to sell
                if(standard.getNumUpgrades() > 0) {
                    sellUpgradeButton.draw(g, observer);
                    sellUpgradeButton.setActive(true);
                } else {
                    sellUpgradeButton.setActive(false);
                }
                // Check if there are upgrades to buy
                if(standard.getNumUpgrades() != 5 && monopoly) {
                    buyUpgradeButton.draw(g, observer);
                    buyUpgradeButton.setActive(true);
                } else {
                    buyUpgradeButton.setActive(false);
                }
            }

            // Check if the property can be mortgaged or unmortgaged
            if(property.getMortgaged()) {
                unMortgageButton.draw(g, observer);
                unMortgageButton.setActive(true);
                mortgageButton.setActive(false);
            } else {
                mortgageButton.draw(g, observer);
                unMortgageButton.setActive(false);
                mortgageButton.setActive(true);
            }
        }
    }

}
