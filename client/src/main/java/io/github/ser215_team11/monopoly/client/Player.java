package io.github.ser215_team11.monopoly.client;

/**
 * Represents a single player on the board.
 */
public class Player {
	String name;
	int token_type;
	int pos_of_player;
	int money;
	int getOutOfJail;
	int turns_left_in_jail;
	boolean still_in_game;
	Property the_array[];
	
	public Player()//default constructor
	{
		pos_of_player=0;
		money = 1000;
		getOutOfJail = 0;
		turns_left_in_jail=0;
		still_in_game = true;
		
		Property[] the_array = new Property[0];
	}
	
// setter and getter for token type
	public void set_tokenType(int token)
		{
			token_type = token;
		}
	public int get_tokenType()
		{
			return token_type;
		}
	
	// setter and getter for name
	public void set_name(String playerName)
		{
			name = playerName;
		}
	public String get_name()
		{
			return name;
		}
	
	// setter and getter for pos_of_player
	public void set_pos_of_player(int player_pos)
		{
			pos_of_player = player_pos;
		}
	public int get_pos_of_player()
		{
			return pos_of_player;
		}
	
	// Setter and getter for money
	public void set_money(int amount)
		{
			money = amount;
		}
	public int get_money()
		{
			return money;
		}
	
	// setter and getter for getOutOfJail
	public void set_getOutOfJail(int get_out)
		{
			getOutOfJail = get_out;
		}
	public int get_getOutOfJail()
		{
			return getOutOfJail;
		}
	
	// setter and getter for turns left in jail
	public void set_turns_left_in_jail(int jail_turn_left)
		{
			turns_left_in_jail = jail_turn_left;
		}
	public int get_turns_left_in_jail()
		{
			return turns_left_in_jail;
		}

	// setter and getter for still in game
	public void set_still_in_game(boolean in_jail )
		{
			still_in_game = in_jail;
		}
	public boolean get_still_in_game()
		{
			return still_in_game;
		}
}

