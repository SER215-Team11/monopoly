package io.github.ser215_team11.monopoly.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Tests the general utility library.
 */
public class TestLuaLibrary extends TestCase {

    public TestLuaLibrary(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestLuaLibrary.class);
    }

    /**
     * Tests the not implemented function.
     */
    public void testNotImplementedFunction() {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load(
                "local lib = require(\"io.github.ser215_team11.monopoly.client.LuaLibrary\")\n" +
                "lib.notImplemented()");

        try {
            chunk.call();
            fail("unimplemented script failed to throw exception");
        } catch(LuaError e) {
            if(!(e.getCause() instanceof LuaLibrary.ScriptNotImplementedException)) {
                throw e;
            }
        }
    }

}
