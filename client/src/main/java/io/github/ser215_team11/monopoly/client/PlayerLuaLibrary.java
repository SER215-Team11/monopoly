package io.github.ser215_team11.monopoly.client;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 * A library that gives Lua scripts the power to change the state of a player
 * object.
 */
public class PlayerLuaLibrary extends TwoArgFunction {

	private static Player target = null;

	/**
	 * Returns the player that is set to be modified by future script commands.
	 *
	 * @return player to be modified
	 */
	public static Player getTarget() {
		synchronized(target) {
			return target;
		}
	}

	/**
	 * Sets the player that will be modified by future script commands to the
	 * given player.
	 *
	 * @param target new player to be modified
	 */
	public static void setTarget(Player target) {
		synchronized(target) {
			PlayerLuaLibrary.target = target;
		}
	}

	/**
	 * An empty constructor. If this isn't here, Luaj won't find this library.
	 */
	public PlayerLuaLibrary() {
	}

	/**
	 * Called by the Lua interpreter when this library is included. Does the
	 * necessary initalization steps. We should never need to call this method.
	 *
	 * @param modname the name of this library
	 * @param env the global Lua scope
	 */
	@Override
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();

		// Make functions available to Lua
		library.set("giveMoney", new GiveMoneyFunction());
		library.set("takeMoney", new TakeMoneyFunction());
		library.set("giveGetOutOfJailFreeCard", new GiveGetOutOfJailFreeCardFunction());
		library.set("sendToJail", new SendToJailFunction());
		library.set("getHouseCnt", new GetHouseCntFunction());
		library.set("getHotelCnt", new GetHotelCntFunction());

		return library;
	}

	/**
	 * Implements a function that gives the player money.
	 */
	static class GiveMoneyFunction extends OneArgFunction {
		public GiveMoneyFunction() {
		}

		@Override
		public LuaValue call(LuaValue amount) {
			synchronized(target) {
				int amountInt = amount.checkint();
				target.giveMoney(amountInt);

				return LuaValue.NIL;
			}
		}
	}

	/**
	 * Implements a function that takes money from the player.
	 */
	static class TakeMoneyFunction extends OneArgFunction {
		public TakeMoneyFunction() {
		}

		@Override
		public LuaValue call(LuaValue amount) {
			synchronized(target) {
				int amountInt = amount.checkint();
				target.takeMoney(amountInt);

				return LuaValue.NIL;
			}
		}
	}

	/**
	 * Implements a function that gives the player a Get Out of Jail Free card.
	 */
	static class GiveGetOutOfJailFreeCardFunction extends ZeroArgFunction {
		public GiveGetOutOfJailFreeCardFunction() {
		}

		@Override
		public LuaValue call() {
			synchronized(target) {
				target.giveGetOutOfJailFreeCard();
				return LuaValue.NIL;
			}
		}
	}

	/**
	 * Implements a function that sends the player to jail.
	 */
	static class SendToJailFunction extends ZeroArgFunction {
		public SendToJailFunction() {
		}

		@Override
		public LuaValue call() {
			synchronized(target) {
				target.set_turns_left_in_jail(3);
				return LuaValue.NIL;
			}
		}
	}

	/**
	 * Implements a function that returns the player's total house count.
	 */
	static class GetHouseCntFunction extends ZeroArgFunction {
		public GetHouseCntFunction() {
		}

		@Override
		public LuaValue call() {
			synchronized(target) {
				// TODO: Count the player's total houses when the properties class is implemented
				return LuaValue.valueOf(3);
			}
		}
	}

	/**
	 * Implements a function that returns the player's total hotel count.
	 */
	static class GetHotelCntFunction extends ZeroArgFunction {
		public GetHotelCntFunction() {
		}

		@Override
		public LuaValue call() {
			synchronized(target) {
				// TODO: Count the player's total hotels when the properties class is implemented
				return LuaValue.valueOf(5);
			}
		}
	}

}
