package io.github.ser215_team11.monopoly.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.ArrayList;

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
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		int startMoney = players.get(0).get_money();
		PlayerLuaLibrary.setPlayers(players);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.giveMoney(player.currPlayer(), 500)");

		chunk.call();
		Assert.assertEquals(players.get(0).get_money(), startMoney + 500);
	}

	/**
	 * Tests the take money function.
	 */
	public void testTakeMoneyFunction() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		int startMoney = players.get(0).get_money();
		PlayerLuaLibrary.setPlayers(players);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.takeMoney(player.currPlayer(), 500)");

		chunk.call();
		Assert.assertEquals(players.get(0).get_money(), startMoney - 500);
	}

	/**
	 * Tests the give Get Out of Jail Free card function.
	 */
	public void testGiveGetOutOfJailFreeCardFunction() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		int startCardCnt = players.get(0).getGetOutOfJailFreeCards();
		PlayerLuaLibrary.setPlayers(players);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.giveGetOutOfJailFreeCard(player.currPlayer())");

		chunk.call();
		Assert.assertEquals(players.get(0).getGetOutOfJailFreeCards(), startCardCnt + 1);
	}

	/**
	 * Tests the send to jail function.
	 */
	public void testSendToJailFunction() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		int startJail = players.get(0).getTurnsLeftInJail();
		Assert.assertEquals(startJail, 0);
		PlayerLuaLibrary.setPlayers(players);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.sendToJail(player.currPlayer())");

		chunk.call();
		Assert.assertEquals(players.get(0).getTurnsLeftInJail(), 3);
	}

	/**
	 * Tests the get house count function.
	 */
	public void testGetHouseCntFunction() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		PlayerLuaLibrary.setPlayers(players);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.getHouseCnt(player.currPlayer())");

		chunk.call();
	}

	/**
	 * Tests the get hotel count function.
	 */
	public void testGetHotelCntFunction() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		PlayerLuaLibrary.setPlayers(players);

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.load(
			"local player = require 'io.github.ser215_team11.monopoly.client.PlayerLuaLibrary'\n" +
			"player.getHotelCnt(player.currPlayer())");

		chunk.call();
	}

	/**
	 * Tests that functions that don't exist will throw an error if called.
	 */
	public void testFailsOnNonexistentFunctions() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i=0; i<4; i++) {
			players.add(new Player());
		}
		PlayerLuaLibrary.setPlayers(players);
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
