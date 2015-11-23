local player = require("io.github.ser215_team11.monopoly.client.PlayerLuaLibrary")

curr = player.currPlayer()
player.takeMoney(curr, player.getHouseCnt(curr) * 40 + player.getHotelCnt(curr) * 115)
