package io.github.ser215_team11.monopoly.client;

/**
 * A standard, colored property that can be upgraded with houses and hotels.
 */
public class StandardProperty implements Property {

    private final String name;
    private final String color;
    private final int cost;
    private final int[] rents;
    private final int mortgageRate;
    private final int houseCost;
    private final int hotelCost;

    private int numUpgrades;
    private boolean isMortgaged;

    public StandardProperty(String name, String color, int cost, int[] rents, int mortgageRate, int houseCost, int hotelCost) {
        this.name = name;
        this.color = color;
        this.cost = cost;
        this.rents = rents;
        this.mortgageRate = mortgageRate;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
    }

    public String getName() {
        return name;
    }

    public boolean getMortgaged() {
        return isMortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }

    public int getMortgageRate() {
        return mortgageRate;
    }

    public int getCost() {
        return cost;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    public int getRent(Player owner, int tenantDiceRoll) {
        return rents[numUpgrades];
    }

    public String getColor() {
        return color;
    }

    public int getNumUpgrades() {
        return numUpgrades;
    }

    public void upgrade() {
        if(numUpgrades < 5) {
            numUpgrades++;
        }
    }

    public void downgrade() {
        if(numUpgrades > 0) {
            numUpgrades--;
        }
    }

}
