package io.github.ser215_team11.monopoly.client;

/**
 * A utility property. The rent for these is controlled by the amount of other
 * utilities the owner has and the tenant's dice roll.
 */
public class UtilityProperty implements Property {

    private final String name;
    private final int cost;
    private final int[] rollFactors;
    private final int mortgageRate;

    private boolean isMortgaged;

    public UtilityProperty(String name, int cost, int[] rollFactors, int mortgageRate) {
        this.name = name;
        this.cost = cost;
        this.rollFactors = rollFactors;
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

    public void setMortgaged(boolean isMortgaged) {
        this.isMortgaged = isMortgaged;
    }

    public int getMortgageRate() {
        return mortgageRate;
    }

    public int getRent(Player owner, int tenantDiceRoll) {
        int utilityCnt = 0;
        for(Property p : owner.getProperties()) {
            if(p instanceof UtilityProperty && p != this) {
                utilityCnt++;
            }
        }

        return tenantDiceRoll * rollFactors[utilityCnt];
    }

}
