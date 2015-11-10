local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")

player.takeMoney(player.getHouseCnt() * 25 + player.getHotelCnt() * 100)
