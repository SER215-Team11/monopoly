package io.github.ser215_team11.monopoly.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Tests the player manipulation Lua library.
 */
public class TestPlayerLuaLibrary extends TestCase {

	public TestPlayerLuaLibrary(String testName) {
		super(testName);
	}

	public static Test suite() {
        return new TestSuite(TestPlayerLuaLibrary.class);
    }

	/**
	 * Tests that the player library is seen by the Lua interpreter.
	 */
	public void testIsIncludable() {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'");
		chunk.call();
	}

	/**
	 * Tests that player library functions can be called.
	 */
	public void testCanCallFunctions() {
		PlayerLuaLibrary.setTarget(new Player());
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.test()");
		chunk.call();
	}

	/**
	 * Tests that functions that don't exist will throw an error if called.
	 */
	public void testFailsOnNonexistantFunctions() {
		PlayerLuaLibrary.setTarget(new Player());
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.foo()");

		try {
			chunk.call();
			fail("invalid function was called in Lua, but an exception was not thrown");
		} catch(LuaError e) {
		}
	}
}
