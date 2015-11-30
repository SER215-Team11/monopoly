local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")
local board = require("io.github.ser215_team11.monopoly.client.BoardLuaLibrary")
local lib = require("io.github.ser215_team11.monopoly.client.LuaLibrary")

lastPos = player.getPlayerPos(player.currPlayer())

-- Get all the railroad board positions
utilities = {board.getBoardSpace("Electric Company") - lastPos,
    board.getBoardSpace("Water Works") - lastPos}

-- Find the nearest one
nearest = 99999
for i=1,2 do
    if utilities[i] > 0 and utilities[i] < nearest then
        nearest = utilities[i]
    end
end


player.setPlayerPos(player.currPlayer(), nearest)
if lastPos > player.getPlayerPos(player.currPlayer()) then
    -- The player passed Go
    player.giveMoney(player.currPlayer(), 200)
end
