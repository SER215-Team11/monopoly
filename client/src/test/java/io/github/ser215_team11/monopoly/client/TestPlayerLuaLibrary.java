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
	 * Tests the give money function.
	 */
	public void testGiveMoneyFunction() {
		Player player = new Player();
		int startMoney = player.get_money();
		PlayerLuaLibrary.setTarget(player);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.giveMoney(500)");

		chunk.call();
		Assert.assertEquals(player.get_money(), startMoney + 500);
	}

	/**
	 * Tests the take money function.
	 */
	public void testTakeMoneyFunction() {
		Player player = new Player();
		int startMoney = player.get_money();
		PlayerLuaLibrary.setTarget(player);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.takeMoney(500)");

		chunk.call();
		Assert.assertEquals(player.get_money(), startMoney - 500);
	}

	/**
	 * Tests the give Get Out of Jail Free card function.
	 */
	public void testGiveGetOutOfJailFreeCardFunction() {
		Player player = new Player();
		int startCardCnt = player.get_getOutOfJailFree();
		PlayerLuaLibrary.setTarget(player);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.giveGetOutOfJailFreeCard()");

		chunk.call();
		Assert.assertEquals(player.get_getOutOfJailFree(), startCardCnt + 1);
	}

	/**
	 * Tests the send to jail function.
	 */
	public void testSendToJailFunction() {
		Player player = new Player();
		int startJail = player.get_turns_left_in_jail();
		Assert.assertEquals(startJail, 0);
		PlayerLuaLibrary.setTarget(player);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.sendToJail()");

		chunk.call();
		Assert.assertEquals(player.get_turns_left_in_jail(), 3);
	}

	/**
	 * Tests the get house count function.
	 */
	public void testGetHouseCntFunction() {
		Player player = new Player();
		PlayerLuaLibrary.setTarget(player);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.getHouseCnt()");

		chunk.call();
	}

	/**
	 * Tests the get hotel count function.
	 */
	public void testGetHotelCntFunction() {
		Player player = new Player();
		PlayerLuaLibrary.setTarget(player);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.getHotelCnt()");

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
