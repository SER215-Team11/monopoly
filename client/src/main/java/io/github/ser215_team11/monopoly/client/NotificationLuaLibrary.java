package io.github.ser215_team11.monopoly.client;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 * Gives Lua scripts access to notifications.
 */
public class NotificationLuaLibrary extends TwoArgFunction {

    public NotificationLuaLibrary() {

    }

    /**
     * Called by the Lua interpreter when this library is included. Does the
     * necessary initialization steps.
     * @param modname the name of this library
     * @param env the global Lua scope
     */
    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();

        // Make functions available to Lua
        library.set("notify", new NotifyFunction());

        return library;
    }

    /**
     * Implements a function that makes notifications.
     */
    static class NotifyFunction extends OneArgFunction {
        public NotifyFunction() {

        }

        @Override
        public LuaValue call(LuaValue message) {
            String messageStr = message.checkstring().checkjstring();
            Notification.notify(messageStr);
            return LuaValue.NIL;
        }
    }

}
