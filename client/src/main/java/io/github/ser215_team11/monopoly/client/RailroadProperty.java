package io.github.ser215_team11.monopoly.client;

/**
 * A railroad property. The rent for these is dependent on how many of them the
 * player owns.
 */
public class RailroadProperty implements Property {

    private final String name;
    private final int cost;
    private final int[] rents;
    private final int mortgageRate;

    private boolean isMortgaged;

    public RailroadProperty(String name, int cost, int[] rents, int mortgageRate) {
        this.name = name;
        this.cost = cost;
        this.rents = rents;
        this.mortgageRate = mortgageRate;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
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

    public int getRent(Player owner, int tenantDiceRoll) {
        int railroadCnt = 0;
        for(Property p : owner.getProperties()) {
            if(p instanceof RailroadProperty && p != this) {
                railroadCnt++;
            }
        }

        return rents[railroadCnt];
    }

}
