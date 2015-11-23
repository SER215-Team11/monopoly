package io.github.ser215_team11.monopoly.client;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * General Lua utilities.
 */
public class LuaLibrary extends TwoArgFunction {

    /**
     * Thrown when a Lua script is run but not implemented yet.
     */
    public static class ScriptNotImplementedException extends RuntimeException {
        public ScriptNotImplementedException() {
            super("script has not been implemented yet");
        }

        public ScriptNotImplementedException(String script) {
            super(script + " has not been implemented yet");
        }
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
        library.set("notImplemented", new NotImplementedFunction());

        return library;
    }

    /**
     * Implements a function that crashes the program, alerting development
     * that the script has not been implemented.
     */
    static class NotImplementedFunction extends ZeroArgFunction {
        public NotImplementedFunction() {
        }

        @Override
        public LuaValue call() {
            throw new ScriptNotImplementedException();
        }
    }

}
