package io.github.ser215_team11.monopoly.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private static final double ZOOM_OUT_SCALE = 0.35;

    private BoardSpace[] spaces;

    private int x;
    private int y;
    private int origX;
    private int origY;
    private double scale;

    private Sprite center;
    private Sprite border;

    /**
     * Creates a new board instance using the given config file for filling in
     * spaces. There should only be one of these.
     * @param config file where board and space information is stored
     * @throws IOException, FontFormatException lack of resources, this should bubble to the top
     */
    public Board(int x, int y, String config) throws IOException, FontFormatException {
        this.x = x;
        this.origX = x;
        this.y = y;
        this.origY = y;
        this.scale = ZOOM_OUT_SCALE;

        // Open the config file
        byte[] encoded = Files.readAllBytes(Paths.get(Resources.path(config)));
        String data = new String(encoded, StandardCharsets.UTF_8);

        // Start parsing JSON
        JSONObject parent = new JSONObject(data);

        // Get the center image
        String jsonCenterImage = parent.getString("centerImage");
        center = new Sprite(jsonCenterImage);
        // Load the spaces
        JSONArray jsonSpaces = parent.getJSONArray("spaces");
        spaces = new BoardSpace[jsonSpaces.length()];
        if(spaces.length < 8 || (spaces.length - 4) % 4 != 0) {
            // Alert if the config file has a weird number of spaces
            throw new RuntimeException("invalid board space count found in " + config);
        }

        // Create the spaces
        for(int i=0; i<jsonSpaces.length(); i++) {
            JSONObject jsonSpace = jsonSpaces.getJSONObject(i);

            // Load space data
            String type = jsonSpace.getString("type");
            String name = jsonSpace.getString("name");
            String imageLoc;
            try {
                imageLoc = jsonSpace.getString("image");
            } catch(JSONException e) {
                // Try to figure out the image name if it's not provided
                imageLoc = "/images/spaces/" + name.replace(' ', '-').toLowerCase() + ".png";
            }

            Sprite sprite = new Sprite(imageLoc);

            // Create the board space of the right type
            switch(type) {
                case "no op":
                    spaces[i] = new OneOpSpace(name, "/scripts/do-nothing.lua");
                    break;
                case "property":
                    // TODO: Find the property to hook up to the space when Property is implemented
                    spaces[i] = new PropertySpace(null);
                    break;
                case "card":
                    spaces[i] = new CardSpace(name, jsonSpace.getString("config"));
                    break;
                case "one op":
                    spaces[i] = new OneOpSpace(name, jsonSpace.getString("script"));
                    break;
                case "railroad":
                    // TODO: Hook up railroad when the Railroad class is implemented
                    spaces[i] = new PropertySpace(null);
                    break;
                case "utility":
                    // TODO: Hook up utility when the Utility class is implemented
                    spaces[i] = new PropertySpace(null);
                    break;
                default:
                    throw new RuntimeException("invalid space type \"" + type + "\" found in " + config);
            }

            spaces[i].setSprite(sprite);
        }

        constructBoard();
    }

    public void constructBoard() {
        center.reset();
        center.scale(scale);
        center.setX(x);
        center.setY(y);

        int minX = 999999;
        int minY = 999999;
        int maxX = -999999;
        int maxY = -999999;

        for(int i=0; i<spaces.length; i++) {
            Sprite sprite = spaces[i].getSprite();
            sprite.reset();
            sprite.scale(scale);

            // Calculate the space position
            double w = center.getWidth();
            double h = center.getHeight();
            double wCard = sprite.getWidth();
            double hCard = sprite.getHeight();
            double iD = (double) i;
            double xD = (double) x;
            double yD = (double) y;
            double length = spaces.length;
            if(i < spaces.length/4) {
                sprite.setX((int) (xD + w - (iD * wCard)));
                sprite.setY((int) (yD + h));
                sprite.rotate(0);
            } else if(i < spaces.length/2) {
                sprite.setX((int) (xD - hCard));
                sprite.setY((int) (yD + h - ((iD - (length / 4.0)) * wCard)));
                sprite.rotate(1.5708);
            } else if(i < spaces.length * (3.0/4.0)) {
                sprite.setX((int) (xD + ((iD - length / (2.0)) * wCard) - wCard));
                sprite.setY((int) (y - hCard));
                sprite.rotate(3.14159);
            } else if(i < spaces.length) {
                sprite.setX((int) (xD + w));
                sprite.setY((int) (yD + ((iD - length * (3.0/4.0)) * wCard) - wCard));
                sprite.rotate(4.71239);
            }

            if(sprite.getX() + sprite.getWidth() > maxX) {
                maxX = sprite.getX() + sprite.getWidth();
            }
            if(sprite.getY() + sprite.getHeight() > maxY) {
                maxY = sprite.getY() + sprite.getHeight();
            }
            if(sprite.getX() < minX) {
                minX = sprite.getX();
            }
            if(sprite.getY() < minY) {
                minY = sprite.getY();
            }
        }

        BufferedImage borderImg = new BufferedImage(maxX - minX, maxY - minY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = borderImg.createGraphics();
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(6.0f * (float) scale));
        g.drawRect(0, 0, maxX - minX, maxY - minY);
        g.dispose();
        border = new Sprite(borderImg);
        border.setX(minX);
        border.setY(minY);
    }

    /**
     * Draws the board on screen.
     * @param g graphics context
     * @param observer image observer
     */
    public void draw(Graphics g, ImageObserver observer) {
        center.draw(g, observer);
        for(BoardSpace space : spaces) {
            space.draw(g, observer);
        }
        border.draw(g, observer);
    }

    /**
     * Draws the players on their space on the game board.
     * @param players players to draw
     * @param g graphics context
     * @param observer image observer
     */
    public void drawPlayers(ArrayList<Player> players, Graphics g, ImageObserver observer) {
        // Maps to keep track of how many players are on one space
        Map<BoardSpace, Integer> playersOnSpaces = new HashMap<>();
        Map<BoardSpace, Integer> playersDrawnOnSpaces = new HashMap<>();

        // Find out how many players are on each space
        for(Player player : players) {
            BoardSpace currSpace = spaces[player.getPlayerPos()];
            if(playersOnSpaces.containsKey(currSpace)) {
                playersOnSpaces.put(currSpace, playersOnSpaces.get(currSpace) + 1);
            } else {
                playersOnSpaces.put(currSpace, 1);
                playersDrawnOnSpaces.put(currSpace, 0);
            }
        }

        // Draw the players in different positions depending on how many players need to fit on that space
        for(Player player : players) {
            player.getSprite().reset();
            player.getSprite().scale(scale);

            BoardSpace currSpace = spaces[player.getPlayerPos()];
            int x = currSpace.getSprite().getX();
            int y = currSpace.getSprite().getY();
            if(!playersOnSpaces.containsKey(currSpace)) {
                playersOnSpaces.put(currSpace, 1);
            }
            switch(playersOnSpaces.get(currSpace)) {
                case 1:
                    x += (currSpace.getSprite().getWidth() / 2) - (player.getSprite().getWidth() / 2);
                    y += (currSpace.getSprite().getHeight() / 2) - (player.getSprite().getHeight() / 2);
                    break;
                case 2:
                    player.getSprite().scale(0.75);
                    switch(playersDrawnOnSpaces.get(currSpace)) {
                        case 0:
                            x += (currSpace.getSprite().getWidth() / 4) - (player.getSprite().getWidth() / 4);
                            y += (currSpace.getSprite().getHeight() / 4) - (player.getSprite().getHeight() / 4);
                            break;
                        case 1:
                            x += ((currSpace.getSprite().getWidth() * 3) / 4) - ((player.getSprite().getWidth() * 3) / 4);
                            y += ((currSpace.getSprite().getHeight() * 3) / 4) - ((player.getSprite().getHeight() * 3) / 4);
                            break;
                    }
                    break;
                case 3:
                    player.getSprite().scale(0.75);
                    switch(playersDrawnOnSpaces.get(currSpace)) {
                        case 0:
                            x += (currSpace.getSprite().getWidth() / 4) - (player.getSprite().getWidth() / 4);
                            y += (currSpace.getSprite().getHeight() / 4) - (player.getSprite().getHeight() / 4);
                            break;
                        case 1:
                            x += ((currSpace.getSprite().getWidth() * 3) / 4) - ((player.getSprite().getWidth() * 3) / 4);
                            y += (currSpace.getSprite().getHeight() / 4) - (player.getSprite().getHeight() / 4);
                            break;
                        case 2:
                            x += (currSpace.getSprite().getWidth() / 2) - (player.getSprite().getWidth() / 2);
                            y += ((currSpace.getSprite().getHeight() * 3) / 4) - ((player.getSprite().getHeight() * 3) / 4);
                            break;
                    }
                    break;
                case 4:
                    player.getSprite().scale(0.75);
                    switch(playersDrawnOnSpaces.get(currSpace)) {
                        case 0:
                            x += (currSpace.getSprite().getWidth() / 4) - (player.getSprite().getWidth() / 4);
                            y += (currSpace.getSprite().getHeight() / 4) - (player.getSprite().getHeight() / 4);
                            break;
                        case 1:
                            x += ((currSpace.getSprite().getWidth() * 3) / 4) - ((player.getSprite().getWidth() * 3) / 4);
                            y += (currSpace.getSprite().getHeight() / 4) - (player.getSprite().getHeight() / 4);
                            break;
                        case 2:
                            x += (currSpace.getSprite().getWidth() / 4) - (player.getSprite().getWidth() / 4);
                            y += ((currSpace.getSprite().getHeight() * 3) / 4) - ((player.getSprite().getHeight() * 3) / 4);
                            break;
                        case 3:
                            x += ((currSpace.getSprite().getWidth() * 3) / 4) - ((player.getSprite().getWidth() * 3) / 4);
                            y += ((currSpace.getSprite().getHeight() * 3) / 4) - ((player.getSprite().getHeight() * 3) / 4);
                            break;
                    }
            }

            if(playersDrawnOnSpaces.containsKey(currSpace)) {
                playersDrawnOnSpaces.put(currSpace, playersDrawnOnSpaces.get(currSpace) + 1);
            } else {
                playersDrawnOnSpaces.put(currSpace, 1);
            }

            player.getSprite().setX(x);
            player.getSprite().setY(y);
            player.getSprite().draw(g, observer);
        }
    }


    public void zoomPlayer(Player player, int screenWidth, int screenHeight) {
        BoardSpace space = spaces[player.getPlayerPos()];

        scale = 1.0;
        constructBoard();

        x = x - (space.getSprite().getX() + space.getSprite().getWidth() / 2) + (screenWidth / 2);
        y = y - (space.getSprite().getY() + space.getSprite().getHeight() / 2 - (screenHeight / 2));
        constructBoard();
    }

    public void zoomOut() {
        x = origX;
        y = origY;
        scale = ZOOM_OUT_SCALE;
        constructBoard();
    }

    /**
     * Fixes a board position so it wraps around the board if need be.
     * @param pos current position
     * @return corrected position
     */
    public int fixBoardPos(int pos) {
        if(pos < spaces.length) {
            return pos;
        }

        return pos % spaces.length;
    }

    /**
     * Return the board space the given player is on.
     * @param player player in question
     * @return space player is on
     */
    public BoardSpace getBoardSpace(Player player) {
        return spaces[player.getPlayerPos()];
    }

}
