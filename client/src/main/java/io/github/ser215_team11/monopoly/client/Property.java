package io.github.ser215_team11.monopoly.client;

/**
 * A generic, own-able, and mortgage-able property that opposing players pay rent
 * on.
 */
public class Property {

	//things a property knows
	String name;
	int cost;
	int rent;
	int rent1;
	int rent2;
	int rent3;
	int rent4;
	int rentH;
	int mortgage;
	int houseCost;
	int hotelCost;
	int numHouses = 0;
	int numHotels = 0;
	Player owner;
	boolean mortgaged = false;

	//Constructor
	Property(String nm, int cst, int r, int r1, int r2, int r3, int r4, int rH, int m, int house, int hotel) {
		name = nm;
		cost = cst;
		rent = r;
		rent1 = r1;
		rent2 = r2;
		rent3 = r3;
		rent4 = r4;
		rentH = rH;
		mortgage = m;
		houseCost = house;
		hotelCost = hotel;
	}

	//increments numHouses
	void addHouse() {
		numHouses = numHouses + 1;
	}

	//adds a hotel if houses >= 4
	void addHotel() {
		if (numHouses >= 4) {
			numHotels = numHotels + 1;
			numHouses = 0;
		} else {
			// Tell them it is not valid.
		}
	}

	//returns the rent due from landing on the space
	int getRent(Player player) {
		if (player == owner) {
			if (mortgaged == true) {
				return mortgage;
			}
		} else if (mortgaged == false) {
			if (numHouses == 0) {
				return rent;
			} else if (numHouses == 1) {
				return rent1;
			} else if (numHouses == 2) {
				return rent2;
			} else if (numHouses == 3) {
				return rent3;
			} else if (numHouses == 4) {
				return rent4;
			} else if (numHotels == 1) {
				return rentH;
			}
		}

		throw new RuntimeException("could not find property rent");
	}
	
	int mortgage()
	{
		mortgaged = true;
		return cost / 2;
	}
	
	void removeMortgage()
	{
		mortgaged = false;
	}
	
	void buy(Player p)
	{
		owner = p;
	}
}
