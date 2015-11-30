local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")
local lib = require("io.github.ser215_team11.monopoly.client.LuaLibrary")

player.setPlayerPos(player.currPlayer(), 0)
-- The player passed Go
player.giveMoney(player.currPlayer(), 200)
