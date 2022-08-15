package me.bing.wang.www.kotlintest.kotlintest.ui

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.util.ChatPaginator
import java.util.*

class ConfirmationUI(val player: Player,val what:String) {

    init {
        val gui=Bukkit.createInventory(null, 54, "Confirmation For Team")

        val yesButton: ItemStack= ItemStack(Material.EMERALD)
        val yesMeta:ItemMeta?=yesButton.itemMeta
        yesMeta?.let {
            it.setDisplayName("${ChatColor.GREEN}Yes")
            it.setLocalizedName(what)
            it.lore = mutableListOf(
                *ChatPaginator.wordWrap(
                    "You are now confirm with this choose and can not regard later!", 30
                )
            )
            yesButton.itemMeta = it
            gui.setItem(20,yesButton)


        }


        val cancel=ItemStack(Material.BARRIER)
        val cancelMeta:ItemMeta?=cancel.itemMeta
        cancelMeta?.let {
            it.setDisplayName("${ChatColor.RED}Cancel")
            it.setLocalizedName("cancel")
            it.lore= mutableListOf(*ChatPaginator.wordWrap(
                "You are now cancelling selecting!", 30
            ))
            cancel.itemMeta=it
            gui.setItem(24,cancel)
        }

        player.openInventory(gui)


    }

}