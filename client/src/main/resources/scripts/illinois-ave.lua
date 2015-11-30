local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")
local board = require("io.github.ser215_team11.monopoly.client.BoardLuaLibrary")
local lib = require("io.github.ser215_team11.monopoly.client.LuaLibrary")

lastPos = player.getPlayerPos(player.currPlayer())
player.setPlayerPos(player.currPlayer(), board.getBoardSpace("Illinois Avenue"))
if lastPos > player.getPlayerPos(player.currPlayer()) then
    -- The player passed Go
    player.giveMoney(player.currPlayer(), 200)
end
