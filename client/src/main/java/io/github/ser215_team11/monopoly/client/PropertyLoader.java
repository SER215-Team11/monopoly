package io.github.ser215_team11.monopoly.client;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads up properties from a config file.
 */
public class PropertyLoader {

    private static Map<String, Property> properties;

    /**
     * Loads the properties config file.
     * @param configLoc location of the config file
     * @throws IOException config file was not found
     */
    public static void init(String configLoc) throws IOException {
        byte[] encoded = IOUtils.toByteArray(Resources.stream(configLoc));
        String data = new String(encoded, StandardCharsets.UTF_8);

        JSONObject parent = new JSONObject(data);

        JSONArray jsonProperties = parent.getJSONArray("properties");
        properties = new HashMap<>();
        for(int i=0; i<jsonProperties.length(); i++) {
            JSONObject jsonProperty = jsonProperties.getJSONObject(i);

            // Check that there aren't property duplicates
            if(properties.containsKey(jsonProperty.getString("name"))) {
                throw new RuntimeException("duplicate property name " +
                        jsonProperty.getString("name") + " in " + configLoc);
            }

            // Load the properties
            String type = jsonProperty.getString("type");
            if(type.equals("property")) {
                // Property is a standard property
                JSONArray jsonRents = jsonProperty.getJSONArray("rents");
                int[] rents = new int[jsonRents.length()];
                for(int j = 0; j < rents.length; j++) {
                    rents[j] = jsonRents.getInt(j);
                }

                properties.put(jsonProperty.getString("name"),
                        new StandardProperty(jsonProperty.getString("name"),
                                jsonProperty.getString("color"),
                                jsonProperty.getInt("cost"),
                                rents,
                                jsonProperty.getInt("mortgage"),
                                jsonProperty.getInt("houseCost"),
                                jsonProperty.getInt("hotelCost")));

            } else if(type.equals("railroad")) {
                // Property is a railroad
                JSONArray jsonRents = jsonProperty.getJSONArray("rents");
                int[] rents = new int[jsonRents.length()];
                for(int j = 0; j < rents.length; j++) {
                    rents[j] = jsonRents.getInt(j);
                }

                properties.put(jsonProperty.getString("name"),
                        new RailroadProperty(jsonProperty.getString("name"),
                                jsonProperty.getInt("cost"),
                                rents,
                                jsonProperty.getInt("mortgage")));
            } else if(type.equals("utility")) {
                // Property is a utility
                JSONArray jsonFactors = jsonProperty.getJSONArray("rollFactors");
                int[] factors = new int[jsonFactors.length()];
                for(int j = 0; j < factors.length; j++) {
                    factors[j] = jsonFactors.getInt(j);
                }

                properties.put(jsonProperty.getString("name"),
                        new UtilityProperty(jsonProperty.getString("name"),
                                jsonProperty.getInt("cost"),
                                factors,
                                jsonProperty.getInt("mortgage")));
            } else {
                // Property is unknown
                throw new RuntimeException("invalid property type " + type);
            }
        }
    }

    /**
     * Tries to find a property with the given name
     * @param name the name of the property
     * @return property property with the given name
     */
    public static Property getProperty(String name) {
        return properties.get(name);
    }

    public static boolean hasMonopoly(Player player, String color) {
        int colorCnt = 0;
        for(Property p : properties.values()) {
            if(p instanceof  StandardProperty) {
                StandardProperty standard = (StandardProperty) p;
                if(standard.getColor().equals(color)) {
                    colorCnt++;
                }
            }
        }

        int ownedColorCnt = 0;
        ArrayList<Property> playerProps = player.getProperties();
        for(Property p : playerProps) {
            if(p instanceof StandardProperty) {
                StandardProperty standard = (StandardProperty) p;
                if(standard.getColor().equals(color)) {
                    ownedColorCnt++;
                }
            }
        }

        return ownedColorCnt == colorCnt;
    }

}
