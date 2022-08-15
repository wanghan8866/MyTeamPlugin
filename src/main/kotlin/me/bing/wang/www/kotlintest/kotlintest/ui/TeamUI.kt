package me.bing.wang.www.kotlintest.kotlintest.ui

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class TeamUI(player: Player) {
    init {
        val gui:Inventory=Bukkit.createInventory(null,54,"${ChatColor.GOLD}Team Selection")
        var i:Int = 0

        for (teamType in TeamType.values()){
            if(teamType.hidden){
                continue
            }

            val itemStack:ItemStack= ItemStack(teamType.symbol)
            val meta: ItemMeta? =itemStack.itemMeta
            meta?.let {
                it.setDisplayName("${teamType.displayName} ${ChatColor.GRAY} (${TeamManager.teamCounter(teamType,false)}/${TeamManager.maxPeople} players)")
                it.setLocalizedName(teamType.name)
                itemStack.itemMeta = meta
                gui.setItem(19+2*i,itemStack)
                i++

            }
            player.openInventory(gui)

        }
    }
}