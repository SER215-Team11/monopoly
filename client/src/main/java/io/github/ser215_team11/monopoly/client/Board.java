package io.github.ser215_team11.monopoly.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Board {

    private static final double ZOOM_OUT_SCALE = 0.35;

    private BoardSpace[] spaces;

    private int x;
    private int y;

    private Image center;

    /**
     * Creates a new board instance using the given config file for filling in
     * spaces. There should only be one of these.
     * @param config file where board and space information is stored
     * @throws IOException lack of resources, this should bubble to the top
     */
    public Board(int x, int y, String config) throws IOException {
        this.x = x;
        this.y = y;

        // Open the config file
        byte[] encoded = Files.readAllBytes(Paths.get(Resources.path(config)));
        String data = new String(encoded, StandardCharsets.UTF_8);

        // Start parsing JSON
        JSONObject parent = new JSONObject(data);

        // Get the center image
        String jsonCenterImage = parent.getString("centerImage");
        center = Resources.getImage(jsonCenterImage);
        center = ImageUtils.scale(center, ZOOM_OUT_SCALE, ZOOM_OUT_SCALE);
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
            sprite.scale(ZOOM_OUT_SCALE, ZOOM_OUT_SCALE);

            // Calculate the space position
            double w = center.getWidth(null);
            double h = center.getHeight(null);
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
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(center, x, y, observer);
        for(BoardSpace space : spaces) {
            space.draw(g, observer);
        }
    }

}
