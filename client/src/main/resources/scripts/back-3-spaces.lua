local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")
local lib = require("io.github.ser215_team11.monopoly.client.LuaLibrary")
local board = require("io.github.ser215_team11.monopoly.client.BoardLuaLibrary")

pos = board.fixBoardPos(player.getPlayerPos(player.currPlayer()) - 3);
player.setPlayerPos(player.currPlayer(), pos)
