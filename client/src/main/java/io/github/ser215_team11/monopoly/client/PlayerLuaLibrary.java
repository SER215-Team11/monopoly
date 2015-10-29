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
		library.set("test", new TestFunction());

		return library;
	}

	/**
	 * Implements a test function that does nothing. This is used to check if
	 * Lua is able to call functions correctly. This may be removable after the
	 * player class is implemented.
	 */
	static class TestFunction extends ZeroArgFunction {
		/**
		 * An empty constructor. If this isn't here, Luaj won't find this library.
		 */
		public TestFunction() {
		}

		/**
		 * This method is called when the test function is called. This does
		 * nothing. We should never need to call this method.
		 *
		 * @return nil
		 */
		@Override
		public LuaValue call() {
			synchronized(target) {
				return LuaValue.NIL;
			}
		}
	}

}
