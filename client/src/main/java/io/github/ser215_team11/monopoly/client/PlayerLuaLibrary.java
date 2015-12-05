package io.github.ser215_team11.monopoly.client;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.ArrayList;

/**
 * A library that gives Lua scripts the power to change the state of a player
 * object.
 */
public class PlayerLuaLibrary extends TwoArgFunction {

	private static int currPlayer = 0;
	private static ArrayList<Player> players = null;

	/**
	 * Returns the players that the Lua script may modify.
	 *
	 * @return player to be modified
	 */
	public static ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the players the Lua script can modify to the given array.
	 *
	 * @param players players to be modified
	 */
	public static void setPlayers(ArrayList<Player> players) {
		PlayerLuaLibrary.players = players;
	}

	/**
	 * Sets the current player to the given index, representing whose turn it is.
	 * @param currPlayer zero-based player index
     */
	public static void setCurrPlayer(int currPlayer) {
		PlayerLuaLibrary.currPlayer = currPlayer;
	}

	/**
	 * An empty constructor. If this isn't here, Luaj won't find this library.
	 */
	public PlayerLuaLibrary() {
	}

	/**
	 * Called by the Lua interpreter when this library is included. Does the
	 * necessary initialization steps. We should never need to call this method.
	 *
	 * @param modname the name of this library
	 * @param env the global Lua scope
	 */
	@Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();

		// Make functions available to Lua
		library.set("currPlayer", new CurrPlayerFunction());
		library.set("count", new CountFunction());
		library.set("giveMoney", new GiveMoneyFunction());
		library.set("takeMoney", new TakeMoneyFunction());
		library.set("giveGetOutOfJailFreeCard", new GiveGetOutOfJailFreeCardFunction());
		library.set("sendToJail", new SendToJailFunction());
		library.set("getHouseCnt", new GetHouseCntFunction());
		library.set("getHotelCnt", new GetHotelCntFunction());
		library.set("getPlayerPos", new GetPlayerPosFunction());
		library.set("setPlayerPos", new SetPlayerPosFunction());

		return library;
	}

	/**
	 * Implements a function that returns the current player.
	 */
	static class CurrPlayerFunction extends ZeroArgFunction {
		public CurrPlayerFunction() {
		}

		@Override
		public LuaValue call() {
			return LuaValue.valueOf(currPlayer);
		}
	}

	/**
	 * Implements a function that returns the number of players playing.
	 */
	static class CountFunction extends ZeroArgFunction {
		public CountFunction() {
		}

		@Override
		public LuaValue call() {
			return LuaValue.valueOf(players.size());
		}
	}

	/**
	 * Implements a function that gives a player money.
	 */
	static class GiveMoneyFunction extends TwoArgFunction {
		public GiveMoneyFunction() {
		}

		@Override
		public LuaValue call(LuaValue player, LuaValue amount) {
			int playerInt = player.checkint();
			int amountInt = amount.checkint();
			players.get(playerInt).giveMoney(amountInt);

			return LuaValue.NIL;
		}
	}

	/**
	 * Implements a function that takes money from a player.
	 */
	static class TakeMoneyFunction extends TwoArgFunction {
		public TakeMoneyFunction() {
		}

		@Override
		public LuaValue call(LuaValue player, LuaValue amount) {
			int playerInt = player.checkint();
			int amountInt = amount.checkint();
			players.get(playerInt).takeMoney(amountInt);

			return LuaValue.NIL;
		}
	}

	/**
	 * Implements a function that gives a player a Get Out of Jail Free card.
	 */
	static class GiveGetOutOfJailFreeCardFunction extends OneArgFunction {
		public GiveGetOutOfJailFreeCardFunction() {
		}

		@Override
		public LuaValue call(LuaValue player) {
			int playerInt = player.checkint();
			players.get(playerInt).giveGetOutOfJailFreeCard();
			return LuaValue.NIL;
		}
	}

	/**
	 * Implements a function that sends a player to jail.
	 */
	static class SendToJailFunction extends OneArgFunction {
		public SendToJailFunction() {
		}

		@Override
		public LuaValue call(LuaValue player) {
			int playerInt = player.checkint();
			players.get(playerInt).setTurnsLeftInJail(3);
			return LuaValue.NIL;
		}
	}

	/**
	 * Implements a function that returns a player's total house count.
	 */
	static class GetHouseCntFunction extends OneArgFunction {
		public GetHouseCntFunction() {
		}

		@Override
		public LuaValue call(LuaValue player) {
			int cnt = 0;
			for(Property property : players.get(player.checkint()).getProperties()) {
				if(property instanceof StandardProperty) {
					StandardProperty standard = (StandardProperty) property;
					if(standard.getNumUpgrades() != 5) {
						cnt += standard.getNumUpgrades();
					}
				}
			}
			return LuaValue.valueOf(cnt);
		}
	}

	/**
	 * Implements a function that returns the player's total hotel count.
	 */
	static class GetHotelCntFunction extends OneArgFunction {
		public GetHotelCntFunction() {
		}

		@Override
		public LuaValue call(LuaValue player) {
			int cnt = 0;
			for(Property property : players.get(player.checkint()).getProperties()) {
				if(property instanceof StandardProperty) {
					StandardProperty standard = (StandardProperty) property;
					if(standard.getNumUpgrades() == 5) {
						cnt++;
					}
				}
			}
			return LuaValue.valueOf(cnt);
		}
	}

	static class GetPlayerPosFunction extends OneArgFunction {
		public GetPlayerPosFunction() {
		}

		@Override
		public LuaValue call(LuaValue player) {
			int playerInt = player.checkint();
			return LuaValue.valueOf(players.get(playerInt).getPlayerPos());
		}
	}

	static class SetPlayerPosFunction extends TwoArgFunction {
		public SetPlayerPosFunction() {
		}

		@Override
		public LuaValue call(LuaValue player, LuaValue pos) {
			int playerInt = player.checkint();
			int posInt = pos.checkint();
			players.get(playerInt).setPlayerPos(posInt);
			return LuaValue.NIL;
		}
	}

}
