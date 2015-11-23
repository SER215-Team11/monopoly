package io.github.ser215_team11.monopoly.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Tests that all scripts in the /resources/scripts directory can be run without
 * error.
 */
public class TestScriptsRun extends TestCase {

	public TestScriptsRun(String testName) {
		super(testName);
	}

	public static Test suite() {
        return new TestSuite(TestScriptsRun.class);
    }

	/**
	 * Tests scripts for syntax errors by running them.
	 */
	public void testScriptsRun() throws IOException, FontFormatException {
		// Loop through every script in the scripts directory
		Files.walk(Paths.get(Resources.path("/scripts/"))).forEach(filePath -> {
			// Check that the "file" is actually a file and not a directory
			if(Files.isRegularFile(filePath)) {
				System.out.println("Testing " + filePath);
				// Read the data from the file
				byte[] data = {};
				try {
					data = Files.readAllBytes(filePath);
				} catch(IOException e) {
					// Having lambdas throw exceptions is complicated so I get around that with this workaround
					fail(e.getMessage());
				}
				String script = new String(data, StandardCharsets.UTF_8);

				// Set up the Lua environment and run the script
				ArrayList<Player> players = new ArrayList<>();
				for(int i=0; i<4; i++) {
					players.add(new Player());
				}
				PlayerLuaLibrary.setPlayers(players);
				try {
					BoardLuaLibrary.setBoard(new Board(0, 0, "/config/board.json"));
				} catch(Exception e) {
					fail(e.getMessage());
				}
				Globals globals = JsePlatform.standardGlobals();
				LuaValue chunk = globals.load(script);
				try {
					chunk.call();
				} catch(LuaError e) {
					if(!(e.getCause() instanceof LuaLibrary.ScriptNotImplementedException)) {
						throw e;
					}
				}
			}
		});
	}

}
