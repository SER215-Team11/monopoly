package io.github.ser215_team11.monopoly.client;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * Allows Lua scripts to interact with the board.
 */
public class BoardLuaLibrary extends TwoArgFunction {

    private static Board board;

    public BoardLuaLibrary() {

    }

    public static void setBoard(Board board) {
        BoardLuaLibrary.board = board;
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
        library.set("fixBoardPos", new FixBoardPosFunction());
        library.set("getJailSpace", new GetJailSpaceFunction());
        library.set("getBoardSpace", new GetBoardSpaceFunction());

        return library;
    }

    /**
     * Implements a function that handles board wraparound.
     */
    static class FixBoardPosFunction extends OneArgFunction {
        public FixBoardPosFunction() {

        }

        @Override
        public LuaValue call(LuaValue pos) {
            int posInt = pos.checkint();
            return LuaValue.valueOf(board.fixBoardPos(posInt));
        }
    }

    /**
     * Implements a function that returns the jail space position.
     */
    static class GetJailSpaceFunction extends ZeroArgFunction {
        public GetJailSpaceFunction() {

        }

        @Override
        public LuaValue call() {
            return LuaValue.valueOf(board.getJailSpace());
        }
    }


    static class GetBoardSpaceFunction extends OneArgFunction {
        public GetBoardSpaceFunction() {

        }

        @Override
        public LuaValue call(LuaValue name) {
            return LuaValue.valueOf(board.getBoardPos(name.checkjstring()));
        }
    }
}
