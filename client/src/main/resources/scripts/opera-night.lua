local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")
local lib = require("io.github.ser215_team11.monopoly.client.LuaLibrary")

player.giveMoney(player.currPlayer(), player.count() * 50)
for i=0,player.count()-1 do
	if i ~= player.currPlayer() then
		player.takeMoney(i, 50)
	end
end
